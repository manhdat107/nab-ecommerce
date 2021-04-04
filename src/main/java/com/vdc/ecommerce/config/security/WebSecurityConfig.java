package com.vdc.ecommerce.config.security;

import com.vdc.ecommerce.OAuth2.UserOAuth2;
import com.vdc.ecommerce.OAuth2.UserOAuth2Service;
import com.vdc.ecommerce.service.impl.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserOAuth2Service oAuth2Service;

    private final UserDetailServiceImpl userDetailService;
    private final JwtEntryPoint jwtEntryPoint;

    WebSecurityConfig(UserDetailServiceImpl userDetailService, JwtEntryPoint jwtEntryPoint, UserOAuth2Service oAuth2Service) {
        this.userDetailService = userDetailService;
        this.jwtEntryPoint = jwtEntryPoint;
        this.oAuth2Service = oAuth2Service;
    }

    private static final String[] IGNORE_PATTERN = {
            "/customer/**",
            "/auth/**",
            "/login/**",
    };

    private static final String[] USER_ROLE_PATTERN = {
            "/user/**"
    };
    private static final String[] ADMIN_ROLE_PATTERN = {
            "/admin/**"
    };

    @Bean
    public JwtAuthTokenFilter jwtAuthTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers(IGNORE_PATTERN).permitAll()
                .antMatchers(USER_ROLE_PATTERN).hasAnyRole("USER", "ADMIN")
                .antMatchers(ADMIN_ROLE_PATTERN).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login().loginPage("/login").userInfoEndpoint().userService(oAuth2Service)
                .and()
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    UserOAuth2 userOAuth2 = (UserOAuth2) authentication.getPrincipal();
                    userDetailService.processOAuthPostLogin(userOAuth2, passwordEncoder());
                    httpServletResponse.sendRedirect("/swagger-ui.html");
                });

        http.addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}

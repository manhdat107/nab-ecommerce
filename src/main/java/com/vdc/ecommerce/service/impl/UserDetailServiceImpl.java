package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.OAuth2.UserOAuth2;
import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.common.AuthenticationProvider;
import com.vdc.ecommerce.common.RoleConstant;
import com.vdc.ecommerce.model.security.Role;
import com.vdc.ecommerce.model.security.User;
import com.vdc.ecommerce.model.security.UserPrinciple;
import com.vdc.ecommerce.reposirtory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
//    private final PasswordEncoder encoder;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.encoder = encoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("user not found."));
        return UserPrinciple.build(user);
    }

    public void processOAuthPostLogin(UserOAuth2 userOAuth2, PasswordEncoder encoder) {
        String email = userOAuth2.getEmail();
        String fullName = userOAuth2.getName();
        String userName = email.split("@")[0];
        User existUser = userRepository.findByUsernameOrEmail(null, email);
        if (existUser == null) {
            Set<Role> roles = new HashSet<>();
            Role role = new Role();
            role.setName(RoleConstant.ROLE_USER);
            role.setId(2L);
            roles.add(role);

            User user = new User( userName, encoder.encode("123123"), email, 999999999L, fullName,"" );
            user.setRoles(roles);
            user.setAuthProvider(AuthenticationProvider.FACEBOOK);
            AppUtils.validatorUser(user);
            userRepository.save(user);
        }

    }


}

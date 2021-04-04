package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.common.AuthenticationProvider;
import com.vdc.ecommerce.common.ResponseMessage;
import com.vdc.ecommerce.common.RoleConstant;
import com.vdc.ecommerce.config.security.JwtProvider;
import com.vdc.ecommerce.model.request.SignInRequest;
import com.vdc.ecommerce.model.request.SignUpRequest;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.model.security.JwtResponse;
import com.vdc.ecommerce.model.security.Role;
import com.vdc.ecommerce.model.security.User;
import com.vdc.ecommerce.reposirtory.RoleRepository;
import com.vdc.ecommerce.reposirtory.UserRepository;
import com.vdc.ecommerce.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AccountServiceImpl implements IAccountService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;


    public AccountServiceImpl(UserRepository userRepository, JwtProvider jwtProvider, AuthenticationManager authenticationManager,
                              PasswordEncoder encoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseModel<JwtResponse> signIn(SignInRequest signInRequest) {
        User user = userRepository.findByUsername(signInRequest.getUsername()).orElse(null);
        if (user == null) {
            return ResponseModel.failure("User Not Found");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getUsername(),
                        signInRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseModel.successful(HttpStatus.OK.toString(), new JwtResponse(jwt));
    }

    @Override
    public ResponseModel<?> signUp(SignUpRequest signUpRequest) {
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            return ResponseModel.failure("Those passwords did not match.");
        }

        if (!signUpRequest.getPassword().matches("\\S+$")) {
            return ResponseModel.failure("Password can not contain while space");
        }
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseModel.failure("Username is already exists, Please try again");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseModel.failure("Email is already exists, Please try again");
        }

        User user = new User(
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail(),
                signUpRequest.getPhoneNumber(),
                signUpRequest.getFullName(),
                signUpRequest.getAddress()
        );
        Set<Role> roles = new HashSet<>();
        Set<String> roleInput = signUpRequest.getRoles();


        roleInput.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleConstant.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException(ResponseMessage.AUTH_ROLE_NOT_FIND.getMessage()));
                    roles.add(adminRole);
                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleConstant.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException(ResponseMessage.AUTH_ROLE_NOT_FIND.getMessage()));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        user.setAuthProvider(AuthenticationProvider.LOCAL);
        AppUtils.validatorUser(user);
        userRepository.save(user);
        return ResponseModel.successful(HttpStatus.OK.toString());
    }

}

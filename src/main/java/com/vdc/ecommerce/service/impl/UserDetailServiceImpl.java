package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.model.security.User;
import com.vdc.ecommerce.model.security.UserPrinciple;
import com.vdc.ecommerce.reposirtory.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("user not found."));
        return UserPrinciple.build(user);
    }
}

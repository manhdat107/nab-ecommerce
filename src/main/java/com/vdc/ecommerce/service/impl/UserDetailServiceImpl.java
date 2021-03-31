package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.reposirtory.UserRepository;
import com.vdc.ecommerce.model.security.User;
import com.vdc.ecommerce.model.security.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameAndDelete(s, false).orElseThrow(() -> new UsernameNotFoundException("user not found."));
        return UserPrinciple.build(user);
    }
}

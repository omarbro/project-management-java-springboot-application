package com.example.demo.service;
import com.example.demo.model.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService( UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUserName(username);

        if(user == null ){
            throw new UsernameNotFoundException("User not found : "+ username);
        }

        return org.springframework.security.core.userdetails.User
        .withUsername(user.getUserName())
        .password(user.getPassword())
        .roles("USER")
        .build();
    }
}

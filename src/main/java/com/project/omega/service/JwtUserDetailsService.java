package com.project.omega.service;


import com.project.omega.bean.dao.User;
import com.project.omega.bean.dto.UserDTO;
import com.project.omega.exceptions.DuplicateUserException;
import com.project.omega.helper.Constant;
import com.project.omega.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public User createUser(UserDTO user) throws DuplicateUserException {
        String email = user.getEmail();
        if(userRepository.existsByEmail(email)) {
            throw new DuplicateUserException(Constant.ERROR_USER_EXISTS + email);
        }
        User u = new User.UserBuilder()
                .setEmail(user.getEmail())
                .setPassword(bcryptEncoder.encode(user.getPassword()))
                .setRole(user.getRole())
                .build();
        userRepository.save(u);
        return u;
    }
}
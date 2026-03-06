package com.hm.UserMicroservice.Security;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hm.UserMicroservice.repository.UserRepository;
import com.hm.UserMicroservice.entity.User;
;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

   Optional<User> optionalUser = userRepository.findByEmail(username);

if (optionalUser.isEmpty()) {
    throw new UsernameNotFoundException("User not found");
}

User user = optionalUser.get();


        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                // .roles("USER") // Example role
                .build();
    }
}

package com.hm.UserMicroservice.Controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Map;

 
import com.hm.UserMicroservice.DTO.LoginRequest;
import com.hm.UserMicroservice.DTO.UserDTO;
import com.hm.UserMicroservice.Security.JwtUtil;
import com.hm.UserMicroservice.entity.User;
import com.hm.UserMicroservice.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
  private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtUtil.generateToken(request.getUsername());

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
public ResponseEntity<User> register(@Valid @RequestBody UserDTO userDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.createUserFromDTO(userDto));
}

}


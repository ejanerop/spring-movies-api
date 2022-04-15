package com.janero.movies.controllers;

import javax.validation.Valid;
import com.janero.movies.config.JwtTokenUtil;
import com.janero.movies.domain.dto.AuthRequest;
import com.janero.movies.domain.dto.CreateUserRequest;
import com.janero.movies.domain.model.User;
import com.janero.movies.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
            UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody @Valid AuthRequest request) {
        try {
            System.out.println(request.getUsername());
            System.out.println(request.getPassword());
            Authentication authenticate =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()));


            User user = (User) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user))
                    .body(user);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("register")
    public User register(@RequestBody @Valid CreateUserRequest request) {
        return userService.create(request);
    }
}

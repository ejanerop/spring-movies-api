package com.janero.movies.controller;

import javax.validation.Valid;
import javax.validation.ValidationException;
import com.janero.movies.config.JwtTokenUtil;
import com.janero.movies.domain.dto.request.AuthRequest;
import com.janero.movies.domain.dto.request.CreateUserRequest;
import com.janero.movies.domain.dto.response.Response;
import com.janero.movies.domain.dto.response.ResponseMessage;
import com.janero.movies.domain.model.User;
import com.janero.movies.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

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
    @Operation(summary = "Login user", description = "Get access to the API")
    public ResponseEntity<User> login(@RequestBody @Valid AuthRequest request) {
        System.out.println(request.getUsername());
        System.out.println(request.getPassword());
        Authentication authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));


        User user = (User) authenticate.getPrincipal();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user))
                .body(user);
    }

    @PostMapping("register")
    @Operation(summary = "Register user", description = "Register a new user in the API")
    public ResponseEntity<Response> register(@RequestBody @Valid CreateUserRequest request) {
        try {
            User user = userService.create(request);
            return ResponseEntity.ok().body(user);
        } catch (ValidationException e) {
            ResponseMessage message = new ResponseMessage(422, e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
        }
    }
}

package com.janero.movies.handler;

import com.janero.movies.domain.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResponseMessage> handleBadCredentials(BadCredentialsException ex) {
        ResponseMessage message = new ResponseMessage(401, ex.getMessage(), false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
}

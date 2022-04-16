package com.janero.movies;

import com.janero.movies.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthTests {
    @Autowired
    private AuthController controller;

    @Test
    public void authControllerNotNull() throws Exception {
        assertThat(controller).isNotNull();
    }
}

package com.janero.movies.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieEndpointsTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().is3xxRedirection());
    }

    @Test
    public void testGetMoviesEndpointNoAuth() throws Exception {
        this.mockMvc.perform(get("/movies")).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetMovieEndpointNoAuth() throws Exception {
        this.mockMvc.perform(get("/movies/1")).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    public void testGetMoviesEndpointAuth() throws Exception {
        this.mockMvc.perform(get("/movies")).andDo(print()).andExpect(status().isOk());
    }

}

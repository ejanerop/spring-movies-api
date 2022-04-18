package com.janero.movies.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.charset.Charset;
import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.janero.movies.domain.model.Person;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonEndpointsTests {

    public static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetActorsNoAuth() throws Exception {
        this.mockMvc.perform(get("/persons/actors")).andDo(print())
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void testGetDirectorsNoAuth() throws Exception {
        this.mockMvc.perform(get("/persons/directors")).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetPersonNoAuth() throws Exception {
        this.mockMvc.perform(get("/person/1")).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void testSavePersonNoAuth() throws Exception {
        Person person = new Person();
        person.setName("Test");
        person.setBirthday(new Date());
        person.setPlaceOfBirth("test place");
        person.setAdult(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(person);
        this.mockMvc
                .perform(post("/persons").contentType(APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdateNoAuth() throws Exception {
        Person person = new Person();
        person.setId(1L);
        person.setName("Test");
        person.setBirthday(new Date());
        person.setPlaceOfBirth("test place");
        person.setAdult(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(person);
        this.mockMvc
                .perform(put("/persons").contentType(APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteNoAuth() throws Exception {
        this.mockMvc.perform(put("/persons/1")).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    public void testGetActorsAuth() throws Exception {
        this.mockMvc.perform(get("/persons/actors")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void testGetDirectorsAuth() throws Exception {
        this.mockMvc.perform(get("/persons/directors")).andDo(print()).andExpect(status().isOk());
    }



}

package com.janero.movies.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.janero.movies.domain.model.Person;
import com.janero.movies.service.PersonService;
import com.janero.movies.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PersonNoAuthTests {



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

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
        Person person = new Person("Test", "Idle", new Date(), null, "Cuba", true);
        personService.savePerson(person);
        this.mockMvc.perform(get("/persons/" + person.getId())).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testSavePersonNoAuth() throws Exception {
        Person person = new Person("Test", "Idle", new Date(), null, "Cuba", true);
        personService.savePerson(person);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(person);
        this.mockMvc.perform(
                post("/persons").contentType(Constants.APPLICATION_JSON_UTF8).content(requestJson))
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
        this.mockMvc.perform(
                put("/persons").contentType(Constants.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteNoAuth() throws Exception {
        this.mockMvc.perform(put("/persons/1")).andDo(print()).andExpect(status().isUnauthorized());
    }


}

package com.janero.movies.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.janero.movies.domain.model.Person;
import com.janero.movies.domain.request.PersonRequest;
import com.janero.movies.service.PersonService;
import com.janero.movies.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PersonAuthTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PersonService personService;

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

    @Test
    @WithMockUser
    public void testGetPersonAuth() throws Exception {
        Person person = new Person("Test", "Idle", new Date(), null, "Cuba", true);
        personService.savePerson(person);

        this.mockMvc.perform(get("/persons/" + person.getId())).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testSavePersonAuth() throws Exception {
        PersonRequest request = new PersonRequest();
        request.setName("Test");
        request.setBirthday(new Date());
        request.setPlaceOfBirth("test place");
        request.setAdult(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);
        this.mockMvc.perform(
                post("/persons").contentType(Constants.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void testUpdateAuth() throws Exception {
        Person person =
                new Person("TestUpdatePerson", "Just some guy", new Date(), null, "TestLand", true);
        personService.savePerson(person);

        person = personService.getPerson(person.getId());

        PersonRequest request = new PersonRequest();
        request.setName("TestUpdatePersonUpdated");
        request.setBirthday(new Date());
        request.setPlaceOfBirth("Test Island");
        request.setAdult(false);


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(person);
        this.mockMvc
                .perform(put("/persons/" + person.getId())
                        .contentType(Constants.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDeletePersonAuth() throws Exception {
        Person person =
                new Person("TestUserDelete", "test", new Date(), new Date(), "Test place", true);
        personService.savePerson(person);

        Person result = personService.getPerson(person.getId());

        this.mockMvc.perform(delete("/persons/" + result.getId())).andExpect(status().isOk());
    }
}


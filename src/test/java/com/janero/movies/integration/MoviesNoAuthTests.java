package com.janero.movies.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.janero.movies.domain.model.Movie;
import com.janero.movies.domain.model.Person;
import com.janero.movies.domain.request.MovieRequest;
import com.janero.movies.service.MovieService;
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
public class MoviesNoAuthTests {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MovieService movieService;
    @Autowired
    private PersonService personService;

    @Test
    public void testGetMoviesEndpointNoAuth() throws Exception {
        this.mockMvc.perform(get("/movies")).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetMovieEndpointNoAuth() throws Exception {
        Person person =
                new Person("TestDirector", "test", new Date(), new Date(), "Test place", true);
        person = personService.savePerson(person);

        Person director = personService.getPerson(person.getId());

        Movie movie = new Movie("Test Movie", "Test Overview", new Date());
        movie.setDirector(director);
        movieService.saveMovie(movie);

        Movie found = movieService.getMovie(movie.getId());

        this.mockMvc.perform(get("/movies/" + found.getId())).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testSaveMovieEndpointNoAuth() throws Exception {
        Person person =
                new Person("TestDirector", "test", new Date(), new Date(), "Test place", true);
        person = personService.savePerson(person);

        Person director = personService.getPerson(person.getId());

        MovieRequest request = new MovieRequest();
        request.setName("Test Movie");
        request.setOverview("Test Overview");
        request.setReleaseDate(new Date());
        request.setAdult(true);
        request.setBudget(100);
        request.setRevenue(100);
        request.setRuntime(120);
        request.setDirector(director.getId());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(person);
        this.mockMvc.perform(
                post("/movies").contentType(Constants.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void testUpdateMovieNoAuth() throws Exception {
        Person person =
                new Person("TestDirector", "test", new Date(), new Date(), "Test place", true);
        person = personService.savePerson(person);

        Person director = personService.getPerson(person.getId());

        Movie movie = new Movie("Test movie", "Test Overview", new Date());
        movie.setDirector(director);
        movieService.saveMovie(movie);

        movie = movieService.getMovie(movie.getId());

        MovieRequest request = new MovieRequest();
        request.setName("Test Movie2");
        request.setOverview("Test Overview2");
        request.setReleaseDate(new Date());
        request.setAdult(true);
        request.setBudget(120);
        request.setRevenue(120);
        request.setRuntime(120);
        request.setDirector(director.getId());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);
        this.mockMvc
                .perform(put("/movies/" + movie.getId())
                        .contentType(Constants.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteMovieAuth() throws Exception {
        Person person =
                new Person("TestDirector", "test", new Date(), new Date(), "Test place", true);
        person = personService.savePerson(person);

        Person director = personService.getPerson(person.getId());

        Movie movie = new Movie("Test movie", "Test Overview", new Date());
        movie.setDirector(director);
        movieService.saveMovie(movie);

        movie = movieService.getMovie(movie.getId());

        this.mockMvc.perform(delete("/movies/" + movie.getId()))
                .andExpect(status().isUnauthorized());
    }
}

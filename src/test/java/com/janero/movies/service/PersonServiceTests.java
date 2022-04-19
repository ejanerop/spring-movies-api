package com.janero.movies.service;

import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import com.janero.movies.domain.model.Constants;
import com.janero.movies.domain.model.Movie;
import com.janero.movies.domain.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
public class PersonServiceTests {

    @Autowired
    private PersonService personService;
    @Autowired
    private MovieService movieService;

    @Test
    public void contextLoads() {}

    @Test
    public void testSaveNullPerson() {
        assertThrows(IllegalArgumentException.class, () -> personService.savePerson(null));
    }

    @Test
    public void testSaveEmptyPerson() {
        assertThrows(IllegalArgumentException.class, () -> personService.savePerson(new Person()));
    }

    @Test
    public void testSaveAndGetPerson() {
        Person person = new Person();
        person.setName("Eric");
        person.setBiography("biography");
        person.setAdult(true);
        person.setBirthday(new Date());
        personService.savePerson(person);


        Person found = personService.getPerson(person.getId());

        assertThat(person.getId()).isEqualTo(found.getId());
    }

    @Test
    public void testDeleteNullPerson() {
        assertThrows(IllegalArgumentException.class, () -> personService.deletePerson(null));
    }

    @Test
    public void testDeleteEmptyPerson() {
        assertThrows(IllegalArgumentException.class,
                () -> personService.deletePerson(new Person()));
    }

    @Test
    public void testDeletePerson() {
        Person person = new Person();
        person.setName("Eric");
        person.setBiography("biography");
        person.setAdult(true);
        person.setBirthday(new Date());
        personService.savePerson(person);

        Person found = personService.getPerson(person.getId());

        assertThat(person.getId()).isEqualTo(found.getId());

        personService.deletePerson(found);

        assertThrows(NoSuchElementException.class, () -> personService.getPerson(found.getId()));
    }

    @Test
    public void testUpdatePerson() {
        Person person = new Person();
        person.setName("Eric");
        person.setBiography("biography");
        person.setAdult(true);
        person.setBirthday(new Date());
        personService.savePerson(person);

        Person found = personService.getPerson(person.getId());

        assertThat(person.getId()).isEqualTo(found.getId());

        found.setName("Eric2");
        found.setBiography("biography2");
        found.setAdult(false);
        found.setBirthday(new Date());
        personService.savePerson(found);

        Person updated = personService.getPerson(person.getId());


        assertAll(() -> {
            assertThat(updated.getName()).isEqualTo("Eric2");
            assertThat(updated.getBiography()).isEqualTo("biography2");
            assertThat(updated.getAdult()).isEqualTo(false);
        });
    }

    @Test
    public void testGetActors() {
        Person actor = new Person("actor", "actor", new Date(), new Date(), "Test place");
        personService.savePerson(actor);
        Person actor2 = new Person("actor2", "actor", new Date(), new Date(), "Test place");
        personService.savePerson(actor2);
        Person actor3 = new Person("actor3", "actor", new Date(), new Date(), "Test place");
        personService.savePerson(actor3);

        Person director = new Person("director", "director", new Date(), new Date(), "Test place2");
        personService.savePerson(director);

        Set<Person> actors = new HashSet<Person>();
        actors.add(actor);
        actors.add(actor2);
        actors.add(actor3);

        Movie movie = new Movie("Test Movie", "test overview", new Date());
        movie.setDirector(director);
        movie.setActors(actors);

        movieService.saveMovie(movie);

        Page<Person> result = personService.getActors(new Person(),
                PageRequest.of(0, Constants.DEFAULT_PAGE_SIZE));

        assertThat(result.getNumberOfElements()).isEqualTo(3);

    }

    @Test
    public void testGetDirectors() {

        Person director = new Person("director", "director", new Date(), new Date(), "Test place");
        personService.savePerson(director);
        Person director2 =
                new Person("director2", "director", new Date(), new Date(), "Test place2");
        personService.savePerson(director2);

        Movie movie = new Movie("Test Movie", "test overview", new Date());
        movie.setDirector(director);
        movieService.saveMovie(movie);
        Movie movie2 = new Movie("Test Movie2", "test overview", new Date());
        movie2.setDirector(director2);
        movieService.saveMovie(movie2);


        Page<Person> result = personService.getDirectors(new Person(),
                PageRequest.of(0, Constants.DEFAULT_PAGE_SIZE));

        assertThat(result.getNumberOfElements()).isEqualTo(2);

    }

}

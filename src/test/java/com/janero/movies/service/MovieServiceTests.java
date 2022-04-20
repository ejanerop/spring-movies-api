package com.janero.movies.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Date;
import java.util.NoSuchElementException;
import com.janero.movies.domain.model.Movie;
import com.janero.movies.domain.model.Person;
import com.janero.movies.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@AutoConfigureTestDatabase
public class MovieServiceTests {

    @Autowired
    private PersonService personService;
    @Autowired
    private MovieService movieService;

    @Test
    public void contextLoads() {}

    @Test
    public void testSaveNullMovie() {
        assertThrows(IllegalArgumentException.class, () -> movieService.saveMovie(null));
    }

    @Test
    public void testSaveEmptyMovie() {
        assertThrows(IllegalArgumentException.class, () -> movieService.saveMovie(new Movie()));
    }

    @Test
    public void testSaveAndGetMovie() {
        Person person =
                new Person("TestDirector", "test", new Date(), new Date(), "Test place", true);
        person = personService.savePerson(person);

        Person director = personService.getPerson(person.getId());

        Movie movie = new Movie("Test Movie", "Test Overview", new Date());
        movie.setDirector(director);
        movieService.saveMovie(movie);

        Movie found = movieService.getMovie(movie.getId());

        assertAll(() -> {
            assertThat(movie.getId()).isEqualTo(found.getId());
            assertThat(movie.getName()).isEqualTo(found.getName());
            assertThat(movie.getOverview()).isEqualTo(found.getOverview());
            assertThat(movie.getDirector().getId()).isEqualTo(found.getDirector().getId());
        });
    }

    @Test
    public void testDeleteNullMovie() {
        assertThrows(IllegalArgumentException.class, () -> movieService.deleteMovie(null));
    }

    @Test
    public void testDeleteEmptyPerson() {
        assertThrows(IllegalArgumentException.class, () -> movieService.deleteMovie(new Movie()));
    }

    @Test
    public void testDeletePerson() {
        Person person =
                new Person("TestDirector", "test", new Date(), new Date(), "Test place", true);
        person = personService.savePerson(person);

        Person director = personService.getPerson(person.getId());

        Movie movie = new Movie("Test Movie", "Test Overview", new Date());
        movie.setDirector(director);
        movieService.saveMovie(movie);

        Movie found = movieService.getMovie(movie.getId());

        assertThat(movie.getId()).isEqualTo(found.getId());

        movieService.deleteMovie(found);

        assertThrows(NoSuchElementException.class, () -> movieService.getMovie(found.getId()));
    }

    @Test
    public void testUpdateMovie() {
        Person person =
                new Person("TestDirector", "test", new Date(), new Date(), "Test place", true);
        person = personService.savePerson(person);

        Person director = personService.getPerson(person.getId());

        Movie movie = new Movie("Test Movie", "Test Overview", new Date());
        movie.setDirector(director);
        movieService.saveMovie(movie);

        Movie found = movieService.getMovie(movie.getId());

        assertAll(() -> {
            assertThat(movie.getId()).isEqualTo(found.getId());
            assertThat(movie.getName()).isEqualTo(found.getName());
            assertThat(movie.getOverview()).isEqualTo(found.getOverview());
        });


        movie.setName("Test Movie 2");
        movie.setOverview("Test Overview 2");
        movieService.saveMovie(movie);

        Movie found2 = movieService.getMovie(movie.getId());

        assertAll(() -> {
            assertThat(movie.getId()).isEqualTo(found2.getId());
            assertThat(movie.getName()).isEqualTo(found2.getName());
            assertThat(movie.getOverview()).isEqualTo(found2.getOverview());
        });
    }

    @Test
    public void testGetMovies() {
        Person person =
                new Person("TestDirector", "test", new Date(), new Date(), "Test place", true);
        person = personService.savePerson(person);

        Person director = personService.getPerson(person.getId());

        Movie movie = new Movie("Test Movie", "Test Overview", new Date());
        movie.setDirector(director);
        movieService.saveMovie(movie);

        assertThat(
                movieService.getMovies(new Movie(), PageRequest.of(0, Constants.DEFAULT_PAGE_SIZE))
                        .getNumberOfElements()).isPositive();
    }



}

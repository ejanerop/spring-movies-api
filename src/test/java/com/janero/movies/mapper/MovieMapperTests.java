package com.janero.movies.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Date;
import java.util.NoSuchElementException;
import com.janero.movies.domain.dto.MovieDTO;
import com.janero.movies.domain.mapper.MovieMapper;
import com.janero.movies.domain.model.Movie;
import com.janero.movies.domain.model.Person;
import com.janero.movies.domain.query.MovieQuery;
import com.janero.movies.domain.request.MovieRequest;
import com.janero.movies.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@AutoConfigureTestDatabase
public class MovieMapperTests {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private PersonService personService;


    @Test
    public void testMaptoDTONullMovie() {
        assertThrows(NullPointerException.class, () -> {
            this.movieMapper.mapToDTO(null);
        });
    }

    @Test
    public void testMaptoDTOEmptyMovie() {
        Movie movie = new Movie();

        assertThrows(NullPointerException.class, () -> {
            this.movieMapper.mapToDTO(movie);
        });
    }

    @Test
    public void testMaptoDTO() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("testMovie");
        movie.setOverview("testOverview");
        movie.setReleaseDate(new Date());
        movie.setAdult(true);
        movie.setBudget(15);
        movie.setRevenue(20);
        movie.setRuntime(20);
        movie.setDirector(new Person());

        MovieDTO movieDTO = this.movieMapper.mapToDTO(movie);

        assertAll(() -> {
            assertEquals(movie.getId(), movieDTO.getId());
            assertEquals(movie.getName(), movieDTO.getName());
            assertEquals(movie.getOverview(), movieDTO.getOverview());
            assertEquals(movie.getReleaseDate(), movieDTO.getReleaseDate());
            assertEquals(movie.getAdult(), movieDTO.getAdult());
            assertEquals(movie.getBudget(), movieDTO.getBudget());
            assertEquals(movie.getRevenue(), movieDTO.getRevenue());
            assertEquals(movie.getRuntime(), movieDTO.getRuntime());
            assertEquals(movie.getDirector().getId(), movieDTO.getDirector().getId());
        });

    }

    @Test
    public void testMaptoDTONoDirector() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("testMovie");
        movie.setOverview("test");
        movie.setReleaseDate(new Date());
        movie.setAdult(false);
        movie.setBudget(0);
        movie.setRevenue(0);
        movie.setRuntime(0);

        assertThrows(NullPointerException.class, () -> {
            this.movieMapper.mapToDTO(movie);
        });
    }

    @Test
    public void testMaptoEntityFromQueryNull() {
        MovieQuery query = null;
        assertThrows(NullPointerException.class, () -> {
            this.movieMapper.mapToEntity(query);
        });
    }

    @Test
    public void testMaptoEntityFromQuery() {
        MovieQuery query = new MovieQuery();
        query.setName("Test Movie");
        query.setOverview("Test Overview");
        query.setYear(2022);
        query.setAdult(true);

        Movie movie = movieMapper.mapToEntity(query);

        assertAll(() -> {
            assertEquals(query.getName(), movie.getName());
            assertEquals(query.getOverview(), movie.getOverview());
            assertEquals(query.getYear(), movie.getReleaseDate().getYear() + 1900);
            assertEquals(query.getAdult(), movie.getAdult());
        });
    }

    @Test
    public void testMaptoEntityFromRequestNull() {
        MovieRequest query = null;
        assertThrows(NullPointerException.class, () -> {
            this.movieMapper.mapToEntity(query);
        });
    }

    @Test
    public void testMaptoEntityFromRequest() {
        Person director = new Person("Test Director", "biography", new Date(), new Date(),
                "Test Place", true);
        director.setName("Test Director");

        personService.savePerson(director);

        MovieRequest query = new MovieRequest();
        query.setName("Test Movie");
        query.setOverview("Test Overview");
        query.setReleaseDate(new Date());
        query.setAdult(true);
        query.setBudget(15);
        query.setRevenue(20);
        query.setRuntime(20);
        query.setDirector(director.getId());

        Movie movie = movieMapper.mapToEntity(query);

        assertAll(() -> {
            assertEquals(query.getName(), movie.getName());
            assertEquals(query.getOverview(), movie.getOverview());
            assertEquals(query.getReleaseDate(), movie.getReleaseDate());
            assertEquals(query.getAdult(), movie.getAdult());
            assertEquals(query.getBudget(), movie.getBudget());
            assertEquals(query.getRevenue(), movie.getRevenue());
            assertEquals(query.getRuntime(), movie.getRuntime());
            assertEquals(query.getDirector(), movie.getDirector().getId());
        });
    }

    @Test
    public void testMaptoEntityFromRequestNoDirector() {

        MovieRequest query = new MovieRequest();
        query.setName("Test Movie");
        query.setOverview("Test Overview");
        query.setReleaseDate(new Date());
        query.setAdult(true);
        query.setBudget(15);
        query.setRevenue(20);
        query.setRuntime(20);

        assertThrows(NoSuchElementException.class, () -> {
            this.movieMapper.mapToEntity(query);
        });
    }
}

package com.janero.movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.NoSuchElementException;
import com.janero.movies.domain.dto.MovieDTO;
import com.janero.movies.domain.dto.criteria.MovieCriteria;
import com.janero.movies.domain.mapper.MovieMapper;
import com.janero.movies.domain.model.Constants;
import com.janero.movies.domain.model.Movie;
import com.janero.movies.service.MovieService;

@RestController
@RequestMapping(path = "/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieMapper movieMapper;

    @GetMapping()
    public @ResponseBody Iterable<MovieDTO> getMovies(MovieCriteria criteria) {

        int page = criteria.getPage() == null ? 0 : criteria.getPage();
        int size = criteria.getSize() == null ? Constants.DEFAULT_PAGE_SIZE : criteria.getSize();

        Movie movie = new Movie();
        movie.setName(criteria.getName());
        movie.setOverview(criteria.getOverview());
        if (criteria.getYear() != null) {
            movie.setYear(criteria.getYear());
        }
        if (criteria.getAdult() != null) {
            movie.setAdult(criteria.getAdult());
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Movie> entities = movieService.getMovies(movie, pageable);

        return entities.map(movieMapper::mapToDTO);
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody MovieDTO getMovie(@PathVariable Long id) {
        try {
            return movieMapper.mapToDTO(movieService.getMovie(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }



}

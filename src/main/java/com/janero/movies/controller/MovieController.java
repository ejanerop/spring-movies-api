package com.janero.movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import com.janero.movies.domain.dto.MovieDTO;
import com.janero.movies.domain.dto.criteria.MovieCriteria;
import com.janero.movies.domain.dto.request.CreateMovieRequest;
import com.janero.movies.domain.dto.response.Response;
import com.janero.movies.domain.dto.response.ResponseMessage;
import com.janero.movies.domain.mapper.MovieMapper;
import com.janero.movies.domain.model.Constants;
import com.janero.movies.domain.model.Movie;
import com.janero.movies.service.MovieService;
import org.springframework.web.bind.annotation.RequestBody;


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

        // TODO: with Mapper
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
    public @ResponseBody ResponseEntity<Response> getMovie(@PathVariable Long id) {
        try {
            MovieDTO movieDTO = movieMapper.mapToDTO(movieService.getMovie(id));
            return ResponseEntity.ok().body(movieDTO);
        } catch (NoSuchElementException e) {
            ResponseMessage message = new ResponseMessage(404, "Movie not found", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @PostMapping()
    public ResponseEntity<Response> saveMovie(@RequestBody @Valid CreateMovieRequest request) {
        try {
            Movie movie = movieMapper.mapToEntity(request);
            movieService.createMovie(movie);
            return ResponseEntity.status(HttpStatus.CREATED).body(movieMapper.mapToDTO(movie));
        } catch (Exception e) {
            e.printStackTrace();
            ResponseMessage message = new ResponseMessage(422, e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
        }
    }



}

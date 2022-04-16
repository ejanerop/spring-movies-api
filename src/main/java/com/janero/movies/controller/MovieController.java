package com.janero.movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import io.swagger.v3.oas.annotations.Operation;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import com.janero.movies.domain.dto.MovieDTO;
import com.janero.movies.domain.dto.query.MovieQuery;
import com.janero.movies.domain.dto.request.MovieRequest;
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
    @Operation(summary = "List movies", description = "Lists all movies paginated")
    public @ResponseBody Page<MovieDTO> getMovies(MovieQuery query) {

        int page = query.getPage() == null ? 0 : query.getPage();
        int size = query.getSize() == null ? Constants.DEFAULT_PAGE_SIZE : query.getSize();

        Movie movie = movieMapper.mapToEntity(query);

        Pageable pageable = PageRequest.of(page, size);

        Page<Movie> entities = movieService.getMovies(movie, pageable);

        return (Page<MovieDTO>) entities.map(movieMapper::mapToDTO);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get movie", description = "Gets a movie information by id")
    public @ResponseBody ResponseEntity<MovieDTO> getMovie(@PathVariable Long id) {
        MovieDTO movieDTO = movieMapper.mapToDTO(movieService.getMovie(id));
        return ResponseEntity.ok().body(movieDTO);
    }

    @PostMapping()
    @Operation(summary = "Add movie", description = "Adds a new movie")
    public ResponseEntity<MovieDTO> saveMovie(@RequestBody @Valid MovieRequest request) {
        Movie movie = movieMapper.mapToEntity(request);
        movieService.saveMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieMapper.mapToDTO(movie));
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update movie", description = "Updates a movie information by id")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id,
            @RequestBody @Valid MovieRequest request) {
        Movie movie = movieMapper.mapToEntity(request);
        movie.setId(id);
        movieService.saveMovie(movie);
        return ResponseEntity.status(HttpStatus.OK).body(movieMapper.mapToDTO(movie));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete movie", description = "Deletes a movie by id")
    public ResponseEntity<ResponseMessage> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(movieService.getMovie(id));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage(204, "Movie deleted", true));
    }


    // ----------------------------------------------------------------------------------
    // Exception handlers
    // ----------------------------------------------------------------------------------

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseMessage> handleNotFound() {
        ResponseMessage message = new ResponseMessage(404, "Movie not found!", false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ResponseMessage> handleBadRequest() {
        ResponseMessage message = new ResponseMessage(422, "Bad Request!", false);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
    }
}

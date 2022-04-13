package com.janero.movies.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/movie")
public class MovieController {
    public static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping()
    public @ResponseBody Iterable<Movie> getMovies(@RequestParam(required = false) String name,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        page = page == null ? 0 : page;
        size = size == null ? DEFAULT_PAGE_SIZE : size;

        Pageable pageable = PageRequest.of(page, size);
        if (name == null) {
            return movieRepository.findAll(pageable);
        } else {
            return movieRepository.findByNameContaining(name, pageable);
        }
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody Movie getMovie(@PathVariable Long id) {

        try {
            return movieRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }



}

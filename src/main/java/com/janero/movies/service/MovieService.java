package com.janero.movies.service;

import java.util.NoSuchElementException;
import com.janero.movies.domain.model.Movie;
import com.janero.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Page<Movie> getMovies(Movie movie, Pageable pageable) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreCase().withIgnoreNullValues();
        return movieRepository.findAll(Example.of(movie, matcher), pageable);
    }

    public Movie getMovie(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movie not found"));
    }

}

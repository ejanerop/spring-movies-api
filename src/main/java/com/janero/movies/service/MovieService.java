package com.janero.movies.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.criteria.Predicate;
import com.janero.movies.domain.model.Movie;
import com.janero.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Page<Movie> getMovies(Movie movie, Pageable pageable) {
        return movieRepository.findAll(getMoviesFromYearAndExample(movie), pageable);
    }

    public Movie getMovie(Long id) {
        if (id == null) {
            throw new NoSuchElementException("Person id is null");
        }
        return movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movie not found!"));
    }

    public Movie saveMovie(Movie movie) {
        if (movie == null || movie.isEmpty()) {
            throw new IllegalArgumentException("Movie is invalid!");
        }
        return movieRepository.save(movie);
    }

    public Specification<Movie> getMoviesFromYearAndExample(Movie movie) {
        return (Specification<Movie>) (root, query, builder) -> {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnorePaths("releaseDate", "budget", "revenue", "runtime").withIgnoreCase()
                    .withIgnoreNullValues();
            final List<Predicate> predicates = new ArrayList<>();
            int year = movie.getYear();
            Date from = new Date(year, 0, 1);
            Date to = new Date(year, 11, 31);

            if (movie.getReleaseDate() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("releaseDate"), from));
                predicates.add(builder.lessThanOrEqualTo(root.get("releaseDate"), to));
            }
            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder,
                    Example.of(movie, matcher)));

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public void deleteMovie(Movie movie) {
        if (movie == null || movie.getId() == null) {
            throw new IllegalArgumentException("Movie is invalid!");
        }
        movieRepository.delete(movie);
    }
}


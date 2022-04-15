package com.janero.movies.repository;

import com.janero.movies.domain.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByNameContaining(String name, Pageable pageable);

}

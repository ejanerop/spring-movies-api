package com.janero.movies.domain.mapper;

import com.janero.movies.domain.dto.MovieDTO;
import com.janero.movies.domain.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class MovieMapper {

    @Autowired
    private PersonMapper personMapper;

    public MovieDTO mapToDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setName(movie.getName());
        movieDTO.setOverview(movie.getOverview());
        movieDTO.setReleaseDate(movie.getReleaseDate());
        movieDTO.setAdult(movie.getAdult());
        movieDTO.setBudget(movie.getBudget());
        movieDTO.setRevenue(movie.getRevenue());
        movieDTO.setRuntime(movie.getRuntime());
        movieDTO.setActors(movie.getActors().stream().map(personMapper::mapToMoviePersonDTO)
                .collect(Collectors.toSet()));
        movieDTO.setDirector(personMapper.mapToMoviePersonDTO(movie.getDirector()));
        return movieDTO;
    };
}

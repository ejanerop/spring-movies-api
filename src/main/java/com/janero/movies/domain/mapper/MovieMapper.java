package com.janero.movies.domain.mapper;

import com.janero.movies.domain.dto.MovieDTO;
import com.janero.movies.domain.dto.query.MovieQuery;
import com.janero.movies.domain.dto.request.MovieRequest;
import com.janero.movies.domain.model.Movie;
import com.janero.movies.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.validation.Valid;

@Service
public class MovieMapper implements Mapper<Movie, MovieDTO> {

    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private PersonService personService;

    @Override
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
        if (movie.getActors() != null) {
            movieDTO.setActors(movie.getActors().stream().map(personMapper::mapToMoviePersonDTO)
                    .collect(Collectors.toSet()));
        }
        movieDTO.setDirector(personMapper.mapToMoviePersonDTO(movie.getDirector()));
        return movieDTO;
    }

    public Movie mapToEntity(@Valid MovieRequest request) throws NoSuchElementException {
        Movie movie = new Movie();
        movie.setName(request.getName());
        movie.setOverview(request.getOverview());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setAdult(request.getAdult());
        movie.setBudget(request.getBudget());
        movie.setRevenue(request.getRevenue());
        movie.setRuntime(request.getRuntime());
        if (request.getActors() != null) {
            movie.setActors(request.getActors().stream().map((id) -> {
                return personService.getPerson(id);
            }).collect(Collectors.toSet()));
        }
        movie.setDirector(personService.getPerson(request.getDirector()));
        return movie;
    }

    public Movie mapToEntity(MovieQuery query) {
        Movie movie = new Movie();
        movie.setName(query.getName());
        movie.setOverview(query.getOverview());
        if (query.getYear() != null) {
            movie.setYear(query.getYear());
        }
        if (query.getAdult() != null) {
            movie.setAdult(query.getAdult());
        }
        return movie;
    }

}

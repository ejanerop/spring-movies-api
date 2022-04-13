package com.janero.movies.movie;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/movie")
public class MovieController {

    @GetMapping()
    public String getMovies(@RequestParam(required = false) String name) {
        return "Movies controller: filtering by name: " + name;
    }


}

package com.janero.movies.movies;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
public @Data class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String overview;
    private Date releaseDate;


}

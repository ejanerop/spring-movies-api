package com.janero.movies.movie;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.janero.movies.person.Person;
import lombok.Data;

@Entity
public @Data class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String overview;
    private Date releaseDate;

    // @ManyToMany(mappedBy = "moviesAsActor")
    // private Set<Person> actors;

    @ManyToOne
    @JoinColumn(name = "director_id", nullable = false)
    private Person director;

}

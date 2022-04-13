package com.janero.movies.person;

import java.sql.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.janero.movies.movie.Movie;
import lombok.Data;

@Entity
public @Data class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String biography;
    private Date birthday;
    private Date deathday;
    private String placeOfBirth;

    // @ManyToMany()
    // @JoinTable(name = "actor_movie", joinColumns = @JoinColumn(name = "person_id"),
    // inverseJoinColumns = @JoinColumn(name = "movie_id"))
    // private Set<Movie> moviesAsActor;

    @OneToMany(mappedBy = "director")
    private Set<Movie> moviesAsDirector;

}

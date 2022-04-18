package com.janero.movies.domain.model;

import java.util.Set;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private @Getter @Setter Long id;
    private @Getter @Setter String name;
    private @Getter @Setter String biography;

    @Temporal(TemporalType.DATE)
    private @Getter @Setter Date birthday;
    @Temporal(TemporalType.DATE)
    private @Getter @Setter Date deathday;
    private @Getter @Setter String placeOfBirth;
    private @Getter @Setter Boolean adult;

    @OneToMany(mappedBy = "director")
    @JsonBackReference
    private Set<Movie> moviesAsDirector;

    @ManyToMany(mappedBy = "actors")
    @JsonBackReference
    private Set<Movie> moviesAsActor;

    public Person() {}

    public Person(String name, String biography, Date birthday, Date deathday,
            String placeOfBirth) {
        this.setName(name);
        this.setBiography(biography);
        this.setBirthday(birthday);
        this.setDeathday(deathday);
        this.setPlaceOfBirth(placeOfBirth);
    }

    public Set<Movie> getMoviesAsDirector() {
        return moviesAsDirector;
    }

    public Set<Movie> getMoviesAsActor() {
        return moviesAsActor;
    }

}

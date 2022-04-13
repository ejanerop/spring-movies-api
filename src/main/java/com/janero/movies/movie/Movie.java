package com.janero.movies.movie;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.janero.movies.person.Person;

@Entity
public class Movie {

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

    public Movie() {

    }

    public Movie(String name, String overview, Date releaseDate) {
        this.setName(name);
        this.setOverview(overview);
        this.setReleaseDate(releaseDate);
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

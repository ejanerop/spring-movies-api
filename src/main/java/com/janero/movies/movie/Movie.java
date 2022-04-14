package com.janero.movies.movie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import com.janero.movies.person.Person;

@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String overview;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @ManyToMany(mappedBy = "moviesAsActor")
    private Set<Person> actors;

    @ManyToOne
    @JoinColumn(name = "director_id", nullable = false)
    private Person director;

    public Movie() {

    }

    public Movie(String name, String overview, String releaseDate) {
        this.setName(name);
        this.setOverview(overview);
        this.setReleaseDate(releaseDate);
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        try {
            this.releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    public void setYear(int year) {
        try {
            this.releaseDate = new SimpleDateFormat("yyyy").parse(String.valueOf(year));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Person getDirector() {
        return this.director;
    }

}

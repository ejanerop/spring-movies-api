package com.janero.movies.person;

import java.util.Set;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.janero.movies.movie.Movie;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String biography;

    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Temporal(TemporalType.DATE)
    private Date deathday;
    private String placeOfBirth;

    @OneToMany(mappedBy = "director")
    private Set<Movie> moviesAsDirector;

    @ManyToMany()
    private Set<Movie> moviesAsActor;

    public Person() {

    }

    public Person(String name, String biography, Date birthday, Date deathday,
            String placeOfBirth) {
        this.setName(name);
        this.setBiography(biography);
        this.setBirthday(birthday);
        this.setDeathday(deathday);
        this.setPlaceOfBirth(placeOfBirth);
    }

    public String getName() {
        return name;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Date getDeathday() {
        return deathday;
    }

    public void setDeathday(Date deathday) {
        this.deathday = deathday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public Set<Movie> getMoviesAsDirector() {
    // return moviesAsDirector;
    // }

    // public Set<Movie> getMoviesAsActor() {
    // return moviesAsActor;
    // }

}

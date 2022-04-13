package com.janero.movies.person;

import java.sql.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.janero.movies.movie.Movie;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String biography;
    private Date birthday;
    private Date deathday;
    private String placeOfBirth;

    @OneToMany(mappedBy = "director")
    private Set<Movie> moviesAsDirector;
    // @ManyToMany()
    // @JoinTable(name = "actor_movie", joinColumns = @JoinColumn(name = "person_id"),
    // inverseJoinColumns = @JoinColumn(name = "movie_id"))
    // private Set<Movie> moviesAsActor;

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

}

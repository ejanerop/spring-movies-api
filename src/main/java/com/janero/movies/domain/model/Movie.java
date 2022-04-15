package com.janero.movies.domain.model;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private @Getter @Setter String name;
    private @Getter @Setter String overview;

    @Temporal(TemporalType.DATE)
    private @Getter Date releaseDate;

    @ManyToMany(mappedBy = "moviesAsActor")
    @JsonManagedReference
    private Set<Person> actors;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "director_id", nullable = false)
    private @Getter Person director;

    public Movie(String name, String overview, String releaseDate) {
        this.setName(name);
        this.setOverview(overview);
        this.setReleaseDate(releaseDate);
    }

    public void setReleaseDate(String releaseDate) {
        try {
            this.releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setYear(int year) {
        try {
            this.releaseDate = new SimpleDateFormat("yyyy").parse(String.valueOf(year));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}

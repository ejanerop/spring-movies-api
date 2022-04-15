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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue
    private @Getter Long id;
    private @Getter @Setter String name;
    private @Getter @Setter String overview;
    @Temporal(TemporalType.DATE)
    private @Getter @Setter Date releaseDate;
    private @Getter @Setter Boolean adult;
    private @Getter @Setter int budget;
    private @Getter @Setter int revenue;
    private @Getter @Setter int runtime;

    @ManyToMany(mappedBy = "moviesAsActor")
    @JsonManagedReference
    private @Getter @Setter Set<Person> actors;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "director_id", nullable = false)
    private @Getter @Setter Person director;

    public Movie(String name, String overview, Date releaseDate) {
        this.setName(name);
        this.setOverview(overview);
        this.setReleaseDate(releaseDate);
    }

    public void setYear(int year) {
        try {
            this.releaseDate = new SimpleDateFormat("yyyy").parse(String.valueOf(year));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @JsonIgnore
    public int getYear() {
        if (this.releaseDate != null) {
            return this.releaseDate.getYear();
        }
        return -1;
    }

}

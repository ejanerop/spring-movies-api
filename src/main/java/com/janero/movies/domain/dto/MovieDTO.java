package com.janero.movies.domain.dto;

import java.util.Date;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MovieDTO {

    private @Getter @Setter Long id;
    private @Getter @Setter String name;
    private @Getter @Setter String overview;
    private @Getter @Setter Date releaseDate;
    private @Getter @Setter Boolean adult;
    private @Getter @Setter int budget;
    private @Getter @Setter int revenue;
    private @Getter @Setter int runtime;
    private @Getter @Setter Set<PersonDTO> actors;
    private @Getter @Setter PersonDTO director;

}

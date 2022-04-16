package com.janero.movies.domain.dto.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class MovieQuery extends Query {

    private @Getter @Setter String name;
    private @Getter @Setter Integer year;
    private @Getter @Setter String overview;
    private @Getter @Setter Boolean adult;

}

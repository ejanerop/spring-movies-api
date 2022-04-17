package com.janero.movies.domain.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class PersonQuery extends Query {

    private @Getter @Setter String name;
    private @Getter @Setter String biography;
    private @Getter @Setter Boolean adult;

}

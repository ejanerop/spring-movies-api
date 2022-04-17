package com.janero.movies.domain.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class Query {

    private @Getter @Setter Integer page;
    private @Getter @Setter Integer size;

}

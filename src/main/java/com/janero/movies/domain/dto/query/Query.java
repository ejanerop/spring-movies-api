package com.janero.movies.domain.dto.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class Query {

    private @Getter @Setter Integer page;
    private @Getter @Setter Integer size;

}

package com.janero.movies.domain.dto.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class Criteria {

    private @Getter @Setter Integer page;
    private @Getter @Setter Integer size;

}

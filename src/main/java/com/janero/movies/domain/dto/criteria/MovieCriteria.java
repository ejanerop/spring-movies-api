package com.janero.movies.domain.dto.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class MovieCriteria extends Criteria {

    private @Getter @Setter String name;
    private @Getter @Setter Integer year;
    private @Getter @Setter String overview;
    private @Getter @Setter Boolean adult;

}

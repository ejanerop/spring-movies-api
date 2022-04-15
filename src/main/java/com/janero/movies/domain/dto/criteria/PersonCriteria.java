package com.janero.movies.domain.dto.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class PersonCriteria extends Criteria {

    private @Getter @Setter String name;
    private @Getter @Setter String biography;
    private @Getter @Setter Boolean adult;

}

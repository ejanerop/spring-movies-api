package com.janero.movies.domain.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.janero.movies.domain.dto.response.Response;

@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PersonDTO implements Response {

    private @Getter @Setter Long id;
    private @Getter @Setter String name;
    private @Getter @Setter String biography;
    private @Getter @Setter Date birthday;
    private @Getter @Setter Boolean adult;
    private @Getter @Setter String placeOfBirth;

}

package com.janero.movies.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserDTO {

    private @Getter @Setter Long id;
    private @Getter @Setter String username;
    private @Getter @Setter String token;


}

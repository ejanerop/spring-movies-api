package com.janero.movies.domain.dto.request;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonRequest implements Request {

    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Birthday is required")
    private Date birthday;
    private String biography;
    private Date deathday;
    @NotEmpty(message = "Place of birth is required")
    private String placeOfBirth;
    @NotNull(message = "Adult is required")
    private Boolean adult;


}

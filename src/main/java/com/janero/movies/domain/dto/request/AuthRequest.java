package com.janero.movies.domain.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {

    @NotNull
    @Email
    private String username;

    @NotNull
    private String password;

}

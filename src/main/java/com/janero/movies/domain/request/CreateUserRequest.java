package com.janero.movies.domain.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;

@Data
public class CreateUserRequest implements Request {

    @NotBlank
    @Email
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String rePassword;
    private Set<String> authorities;

    @JsonIgnore
    public boolean isEmpty() {
        return (username == null || username.isEmpty()) || (password == null || password.isEmpty())
                || (rePassword == null || rePassword.isEmpty());
    }

}

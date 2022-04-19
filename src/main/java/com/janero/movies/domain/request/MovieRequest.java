package com.janero.movies.domain.request;

import java.util.Date;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieRequest implements Request {

    @NotBlank(message = "Name is required")
    private String name;
    private String overview;
    @NotNull(message = "Release date is required")
    private Date releaseDate;
    @NotNull(message = "Adult is required")
    private Boolean adult;
    @NotNull(message = "Budget is required")
    private Integer budget;
    @NotNull(message = "Revenue is required")
    private Integer revenue;
    @NotNull(message = "Runtime is required")
    private Integer runtime;
    private Set<Long> actors;
    @NotNull(message = "Director is required")
    private Long director;
}

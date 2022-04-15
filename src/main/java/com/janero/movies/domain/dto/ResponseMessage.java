package com.janero.movies.domain.dto;

import com.janero.movies.domain.Response;
import lombok.Data;

@Data
public class ResponseMessage implements Response {

    private int status;
    private String message;
    private boolean ok;

    public ResponseMessage(int status, String message, boolean ok) {
        this.status = status;
        this.message = message;
        this.ok = ok;
    }

}

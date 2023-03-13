package org.dejan.axon.domain.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ResponseError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private HttpStatus status;

    ResponseError() {
        timestamp = LocalDateTime.now();
    }

    public ResponseError(String message) {
        message = message;
    }
}

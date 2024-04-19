package com.spring.test.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String url;
    private HttpStatus code;
    private String message;
    private LocalDateTime dateTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> validationErrors;
}

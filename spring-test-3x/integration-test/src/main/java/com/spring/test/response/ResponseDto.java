package com.spring.test.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseDto<T> implements Serializable {
    private String statusCode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String statusMsg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T body;
}

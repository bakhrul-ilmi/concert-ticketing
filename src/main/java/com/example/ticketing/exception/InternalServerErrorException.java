package com.example.ticketing.exception;

import com.example.ticketing.helper.ErrorCodesEnum;
import com.example.ticketing.pojo.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {
    private List<ErrorResponse> errorsResponses;

    // TODO: need to fix goats exception
    public InternalServerErrorException(String response) {
        super(response);
    }

    public InternalServerErrorException(List<ErrorResponse> errorsResponses) {
        this();
        this.errorsResponses = errorsResponses;
    }

    public InternalServerErrorException(ErrorCodesEnum errorsResponse) {
        this();
        this.errorsResponses = new ArrayList<>();
        this.errorsResponses.add(errorsResponse.getErrorResponse());
    }

    public List<ErrorResponse> getErrorsResponses() {
        return this.errorsResponses;
    }

    public InternalServerErrorException() {
        this("Bad Request");
    }
}

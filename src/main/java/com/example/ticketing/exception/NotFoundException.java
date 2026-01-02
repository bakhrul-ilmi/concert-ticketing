package com.example.ticketing.exception;

import com.example.ticketing.helper.ErrorCodesEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private ErrorCodesEnum errorCodesEnum;

    public NotFoundException(String s) {
        super("Not Found : " + s);
    }

    public NotFoundException(ErrorCodesEnum errorCodesEnum) {
        this();
        this.errorCodesEnum = errorCodesEnum;
    }

    public NotFoundException() {
        this("Not Found");
    }

    public ErrorCodesEnum getErrorCodesEnum() {
        return errorCodesEnum;
    }
}

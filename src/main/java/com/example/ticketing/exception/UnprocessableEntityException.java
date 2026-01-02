package com.example.ticketing.exception;

import com.example.ticketing.helper.ErrorCodesEnum;
import com.example.ticketing.pojo.response.MetaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {
    private ErrorCodesEnum errorCodesEnum;
    private MetaResponse metaResponse;

    public UnprocessableEntityException(String s) {
        super("Unprocessable Entity: " + s);
    }

    public UnprocessableEntityException(ErrorCodesEnum errorCodesEnum) {
        this(errorCodesEnum.getErrorResponse().getMessage());
        this.errorCodesEnum = errorCodesEnum;
    }

    public UnprocessableEntityException(ErrorCodesEnum errorCodesEnum, MetaResponse meta) {
        this(errorCodesEnum.getErrorResponse().getMessage());
        this.errorCodesEnum = errorCodesEnum;
        this.metaResponse = meta;
    }

    public ErrorCodesEnum getErrorCodesEnum() {
        return errorCodesEnum;
    }

    public MetaResponse getMetaResponse() {
        return metaResponse;
    }
}

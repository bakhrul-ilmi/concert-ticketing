package com.example.ticketing.helper;

import com.example.ticketing.pojo.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum ErrorCodesEnum {
    SYSTEM_ERROR(ErrorResponse.builder()
            .code(50000)
            .message("Error System")
            .build(), HttpStatus.INTERNAL_SERVER_ERROR),
    TICKET_NOT_AVAILABLE(ErrorResponse.builder()
            .code(50002)
            .message("Ticket not available, pick another seat")
            .build(), HttpStatus.NOT_FOUND),
    TRANSACTION_NOT_PENDING(ErrorResponse.builder()
            .code(50003)
            .message("Transaction not pending")
            .build(), HttpStatus.BAD_REQUEST);

    private final ErrorResponse errorsResponse;
    private final HttpStatus httpStatus;

    ErrorCodesEnum(ErrorResponse errorsResponse, HttpStatus httpStatus) {
        this.errorsResponse = errorsResponse;
        this.httpStatus = httpStatus;
    }

    public ErrorResponse getErrorResponse() {
        return errorsResponse;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

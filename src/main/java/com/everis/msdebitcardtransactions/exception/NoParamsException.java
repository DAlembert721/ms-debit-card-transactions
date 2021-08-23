package com.everis.msdebitcardtransactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoParamsException extends RuntimeException{
    public NoParamsException() {
    }

    public NoParamsException(String message) {
        super(message);
    }
}

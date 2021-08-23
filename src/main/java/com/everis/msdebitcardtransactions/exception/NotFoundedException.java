package com.everis.msdebitcardtransactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundedException extends RuntimeException{
    public NotFoundedException() {
    }

    public NotFoundedException(String className, String number) {
        super(number + " with number: " + number + "can't be founded");
    }

}

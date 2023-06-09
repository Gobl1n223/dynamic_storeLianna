package com.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FirmNotFoundException extends ParentException{

    public FirmNotFoundException(String message) {
        super(message);
    }

    public FirmNotFoundException() {
        super();
    }
}

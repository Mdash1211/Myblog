package com.myblog.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyResultDataAccessException extends RuntimeException {
    public EmptyResultDataAccessException(String message) {
        super(message);
    }
}

package com.utc2.it.ProductManagement.exception;

public class UserAlreadExitsException extends RuntimeException {
    public UserAlreadExitsException(String s) {
        super(s);
    }
}

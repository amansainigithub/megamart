package com.coder.springjwt.exception.customerPanelException;

public class InvalidUsernameAndPasswordException extends RuntimeException {

    public InvalidUsernameAndPasswordException(String message) {
        super(message);
    }
}

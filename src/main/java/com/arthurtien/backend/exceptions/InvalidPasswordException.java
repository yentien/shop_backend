package com.arthurtien.backend.exceptions;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException (String message) {
        super(message);
    }
}

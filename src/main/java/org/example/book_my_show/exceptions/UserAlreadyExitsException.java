package org.example.book_my_show.exceptions;

public class UserAlreadyExitsException extends Exception {
    public UserAlreadyExitsException(String message) {
        super(message);
    }
}

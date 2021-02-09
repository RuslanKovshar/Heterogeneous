package com.kovshar.heterogeneous.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "User with id %s not found!";

    public UserNotFoundException(Long id) {
        super(String.format(MESSAGE, id));
    }
}

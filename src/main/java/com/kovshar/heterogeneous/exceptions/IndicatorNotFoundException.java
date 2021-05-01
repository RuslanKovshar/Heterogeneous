package com.kovshar.heterogeneous.exceptions;

public class IndicatorNotFoundException extends RuntimeException {
    private static final String MESSAGE = "User with id %s not found!";

    public IndicatorNotFoundException(Long id) {
        super(String.format(MESSAGE, id));
    }
}

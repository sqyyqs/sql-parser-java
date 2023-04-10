package com.digdes.school.exception;

import java.io.Serial;

public class ValidationException extends Exception {
    @Serial
    private static final long serialVersionUID = 3614226251354116001L;

    public ValidationException(String message) {
        super(message);
    }
}

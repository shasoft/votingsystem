package ru.shasoft.votingsystem.common.error;

import static ru.shasoft.votingsystem.common.error.ErrorType.NOT_FOUND;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg, NOT_FOUND);
    }
}
package ru.shasoft.votingsystem.common.error;

import static ru.shasoft.votingsystem.common.error.ErrorType.BAD_REQUEST;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String msg) {
        super(msg, BAD_REQUEST);
    }
}
package org.dkf.jolt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal server error")
public class InternalError extends RuntimeException {
    public InternalError(String error) {
        super(error);
    }
}

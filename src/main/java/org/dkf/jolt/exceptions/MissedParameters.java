package org.dkf.jolt.exceptions;

import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Missed required parameter. Requires input, spec and sort")
public class MissedParameters extends RuntimeException {
    public MissedParameters(String arg) {
        super(arg);
    }
}

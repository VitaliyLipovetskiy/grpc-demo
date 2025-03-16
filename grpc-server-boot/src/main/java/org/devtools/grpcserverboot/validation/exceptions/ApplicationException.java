package org.devtools.grpcserverboot.validation.exceptions;

import com.google.rpc.Code;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApplicationException extends RuntimeException {
    private final Code code;
    private final String message;

    public ApplicationException(Code code) {
        this.code = code;
        this.message = "";
    }
}
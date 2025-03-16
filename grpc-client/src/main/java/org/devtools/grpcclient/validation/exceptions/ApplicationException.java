package org.devtools.grpcclient.validation.exceptions;

import io.grpc.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ApplicationException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;
    private final Status grpcStatus;
    private final Map<Status.Code, HttpStatus> grpsToHttpStatusMap = Map.of(
            Status.Code.INVALID_ARGUMENT, HttpStatus.BAD_REQUEST,                       //  3 -> 400
            Status.Code.ABORTED, HttpStatus.NOT_ACCEPTABLE,                             // 10 -> 406
            Status.Code.UNAUTHENTICATED, HttpStatus.UNAUTHORIZED,                       // 16 -> 401
            Status.Code.PERMISSION_DENIED, HttpStatus.FORBIDDEN,                        //  7 -> 403
            Status.Code.NOT_FOUND, HttpStatus.NOT_FOUND,                                //  5 -> 404 -> 204
            Status.Code.ALREADY_EXISTS, HttpStatus.CONFLICT,                            //  6 -> 409
            Status.Code.CANCELLED, HttpStatus.UNPROCESSABLE_ENTITY,                     //  1 -> 422
            Status.Code.OUT_OF_RANGE, HttpStatus.TOO_MANY_REQUESTS,                     // 11 -> 429
            Status.Code.UNKNOWN, HttpStatus.BAD_GATEWAY,                                //  2 -> 502
            Status.Code.UNAVAILABLE, HttpStatus.GATEWAY_TIMEOUT                         // 14 -> 504
    );

    public ApplicationException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.grpcStatus = Status.UNKNOWN;
    }

    public ApplicationException(Status grpcStatus, String message) {
        this.message = message;
        this.grpcStatus = grpcStatus;
        this.httpStatus = grpsToHttpStatusMap.getOrDefault(grpcStatus.getCode(), HttpStatus.BAD_REQUEST);
    }
}

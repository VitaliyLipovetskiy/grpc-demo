package org.devtools.grpcclientboot;

import io.grpc.Status;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;

@SpringBootApplication
public class GrpcClientBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientBootApplication.class, args);
    }

    @Bean
    public GrpcExceptionHandler globalInterceptor() {
        return exception -> {
            if (exception instanceof IllegalArgumentException) {
                return Status.INVALID_ARGUMENT.withDescription(exception.getMessage());
            }
            return null;
        };
    }
}

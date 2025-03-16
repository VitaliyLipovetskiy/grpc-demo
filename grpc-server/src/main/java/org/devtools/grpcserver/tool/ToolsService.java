package org.devtools.grpcserver.tool;

import com.google.rpc.Code;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devtools.gateway.BiDirectionalRequest;
import org.devtools.gateway.BiDirectionalResponse;
import org.devtools.grpcserver.validation.exceptions.ApplicationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ToolsService {
    private final List<String> worlds = List.of(
            "in aliquip voluptate",
            "veniam sit incididunt pariatur",
            "cupidatat dolore et",
            "eu Excepteur in adipisicing",
            "eu ullamco in quis",
            "non dolore ipsum Lorem ut",
            "minim proident nisi Duis nostrud",
            "dolore id dolor enim",
            "ut exercitation sit est ullamco",
            "dolore Lorem aliquip id qui"
    );
    private final Random random = new Random();

    public void biDirectionalStream(BiDirectionalRequest request, StreamObserver<BiDirectionalResponse> responseObserver) {
        log.info("generate {}", request);
        switch (request.getRequestCase()) {
            case MESSAGE ->
                    handleMessage(request.getMessage(), responseObserver);
            case COUNT ->
                    handleGenerateMessages(request.getCount(), responseObserver);
            default -> {
                log.error("Bad request");
                throw new ApplicationException(Code.CANCELLED, "Bad request");
            }
        }
    }

    private void handleMessage(String message, StreamObserver<BiDirectionalResponse> responseObserver) {
        var response = BiDirectionalResponse.newBuilder()
                .setMessage(message)
                .build();
        responseObserver.onNext(response);
    }

    private void handleGenerateMessages(int count, StreamObserver<BiDirectionalResponse> responseObserver) {
        log.info("handleGenerateMessages {}", count);
        for (int i = 0; i < count; i++) {
            int randomInt = random.nextInt(9);
            String message = worlds.get(randomInt);
            var builder = BiDirectionalResponse.newBuilder()
                    .setMessage(message);
            if (i % 3 == 1) {
                builder.setDescription("Description");
            }
            responseObserver.onNext(builder.build());
        }
    }
}

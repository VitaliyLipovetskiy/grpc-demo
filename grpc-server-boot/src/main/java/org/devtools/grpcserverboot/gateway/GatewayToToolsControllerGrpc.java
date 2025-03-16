package org.devtools.grpcserverboot.gateway;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devtools.gateway.BiDirectionalRequest;
import org.devtools.gateway.BiDirectionalResponse;
import org.devtools.gateway.GatewayToToolsGrpc;
import org.devtools.grpcserverboot.tool.ToolsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GatewayToToolsControllerGrpc extends GatewayToToolsGrpc.GatewayToToolsImplBase {
    private final ToolsService toolsService;

    @Override
    public StreamObserver<BiDirectionalRequest> biDirectionalStream(StreamObserver<BiDirectionalResponse> responseObserver) {
        log.info("biDirectionalStream");
        return new StreamObserver<BiDirectionalRequest>() {

            @Override
            public void onNext(BiDirectionalRequest request) {
                log.info("biDirectionalStream onNext {}", request);
                if (request.getType().equalsIgnoreCase("end")) {
                    responseObserver.onCompleted();
                    return;
                }
                toolsService.biDirectionalStream(request, responseObserver);
            }

            @Override
            public void onError(Throwable t) {
                log.error("biDirectionalStream onError", t);
            }

            @Override
            public void onCompleted() {
                log.info("biDirectionalStream onCompleted");
                responseObserver.onCompleted();
            }
        };
    }
}

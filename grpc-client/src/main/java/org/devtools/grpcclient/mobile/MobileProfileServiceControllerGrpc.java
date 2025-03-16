package org.devtools.grpcclient.mobile;

import com.google.rpc.Status;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.devtools.common.Empty;
import org.devtools.grpc.mobile.MobileProfileRequest;
import org.devtools.grpc.mobile.MobileProfileResponse;
import org.devtools.grpc.mobile.MobileProfileServiceGrpc;
import org.devtools.grpcclient.profile.ProfileService;
import org.devtools.grpcclient.validation.exceptions.ApplicationException;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class MobileProfileServiceControllerGrpc extends MobileProfileServiceGrpc.MobileProfileServiceImplBase {
    private final ProfileService profileService;

    @Override
    public void mobileGetProfileById(MobileProfileRequest request, StreamObserver<MobileProfileResponse> responseObserver) {
        log.info("mobileGetProfileById {}", request);
        try {
            MobileProfileResponse response = profileService.getProfileById(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (ApplicationException ae) {
            log.warn("mobileGetProfileById Exception code: {}", ae.getGrpcStatus().getCode(), ae);
            responseObserver.onError(StatusProto.toStatusRuntimeException(getStatus(ae)));
        }
    }

    @Override
    public void getAllProfiles(Empty request, StreamObserver<MobileProfileResponse> responseObserver) {
        log.info("getAllProfiles {}", request);
        try {
            profileService.getAllProfiles(request)
                    .forEach(responseObserver::onNext);
            responseObserver.onCompleted();
        } catch (ApplicationException ae) {
            log.warn("getAllProfiles Exception code: {}", ae.getGrpcStatus().getCode(), ae);
            responseObserver.onError(StatusProto.toStatusRuntimeException(getStatus(ae)));
        }
    }

    private Status getStatus(ApplicationException ae) {
        return Status.newBuilder()
                .setCode(ae.getGrpcStatus().getCode().value())
                .setMessage(ae.getMessage())
                .build();
    }
}

package org.devtools.grpcserverboot.gateway;

import com.google.rpc.Status;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devtools.common.Empty;
import org.devtools.grpc.ProfileRequest;
import org.devtools.grpc.ProfileResponse;
import org.devtools.grpc.ProfileServiceGrpc;
import org.devtools.grpcserverboot.profile.ProfileService;
import org.devtools.grpcserverboot.validation.exceptions.ApplicationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceControllerGrpc extends ProfileServiceGrpc.ProfileServiceImplBase {
    private final ProfileService profileService;

    @Override
    public void getProfileById(ProfileRequest request, StreamObserver<ProfileResponse> responseObserver) {
        log.info("getProfileById {}", request);
        try {
            ProfileResponse response = profileService.getCurrentProfile(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (ApplicationException ae) {
            log.warn("getProfileById Exception code: {}", ae.getCode(), ae);
            responseObserver.onError(StatusProto.toStatusRuntimeException(getStatus(ae)));
        }
    }

    @Override
    public void getAllProfiles(Empty request, StreamObserver<ProfileResponse> responseObserver) {
        log.info("getAllProfiles {}", request);
        try {
            profileService.getAllProfiles()
                    .forEach(responseObserver::onNext);
            responseObserver.onCompleted();
        } catch (ApplicationException ae) {
            log.warn("getAllProfiles Exception code: {}", ae.getCode(), ae);
            responseObserver.onError(StatusProto.toStatusRuntimeException(getStatus(ae)));
        }
    }

    private Status getStatus(ApplicationException ae) {
        return Status.newBuilder()
                .setCode(ae.getCode().getNumber())
                .setMessage(ae.getMessage())
                .build();
    }
}

package org.devtools.grpcclient.profile;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import org.devtools.common.Empty;
import org.devtools.grpc.ProfileRequest;
import org.devtools.grpc.ProfileResponse;
import org.devtools.grpc.ProfileServiceGrpc;
import org.devtools.grpcclient.validation.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class ProfileClientGrpc {
    private final ProfileServiceGrpc.ProfileServiceBlockingStub blockingStub;
    private final long deadline;

    public ProfileClientGrpc(
            @Value("${app.grpc.server.client.host}") String host,
            @Value("${app.grpc.server.client.port}") int port,
            @Value("${app.grpc.deadline}") long deadline,
            @Value("${app.grpc.keep-alive-time}") Long keepAliveTime) {
        this.deadline = deadline;
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .idleTimeout(Long.MAX_VALUE, TimeUnit.MILLISECONDS)
                .keepAliveTime(keepAliveTime, TimeUnit.MILLISECONDS)
                .usePlaintext()
                .build();
        blockingStub = ProfileServiceGrpc.newBlockingStub(channel);
        channel.getState(true);
    }

    public ProfileResponse getProfileById(ProfileRequest request) {
        log.info("getProfileById {}", request);
        try {
            return blockingStub
                    .withDeadlineAfter(deadline, TimeUnit.MILLISECONDS)
                    .getProfileById(request);
        } catch (io.grpc.StatusRuntimeException ex) {
            log.warn("Failed getProfileById: code {}", ex.getStatus().getCode(), ex);
            throw new ApplicationException(ex.getStatus(), "Failed to get all tasks by integration ids: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Failed getProfileById", ex);
            throw new ApplicationException(Status.UNKNOWN, ex.getMessage());
        }
    }

    public List<ProfileResponse> getAllProfiles(Empty request) {
        log.info("getAllProfiles {}", request);
        try {
            Iterable<ProfileResponse> iterable = () -> blockingStub
                    .withDeadlineAfter(deadline, TimeUnit.MILLISECONDS)
                    .getAllProfiles(request);
            return StreamSupport.stream(iterable.spliterator(), false).toList();
        } catch (io.grpc.StatusRuntimeException ex) {
            log.warn("Failed getAllProfiles: code {}", ex.getStatus().getCode(), ex);
            throw new ApplicationException(ex.getStatus(), "Failed getAllProfiles: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Failed getAllProfiles", ex);
            throw new ApplicationException(Status.UNKNOWN, ex.getMessage());
        }
    }

}

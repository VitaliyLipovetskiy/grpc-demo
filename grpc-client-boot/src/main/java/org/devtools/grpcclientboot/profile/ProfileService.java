package org.devtools.grpcclientboot.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devtools.common.Empty;
import org.devtools.grpc.ProfileRequest;
import org.devtools.grpc.ProfileResponse;
import org.devtools.grpcclientboot.mapper.ProfileMapper;
import org.devtools.grpcclientboot.mobile.MobileProfileRequest;
import org.devtools.grpcclientboot.mobile.MobileProfileResponse;
import org.devtools.grpcclientboot.profile.dto.ProfileDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    private final ProfileClientGrpc profileClientGrpc;
    public final ProfileMapper profileMapper;

    public MobileProfileResponse getProfileById(MobileProfileRequest mobileRequest) {
        log.info("getProfileById {}", mobileRequest);
        var request = ProfileRequest.newBuilder()
                .setId(mobileRequest.getId())
                .build();
        ProfileResponse response = profileClientGrpc.getProfileById(request);
        return profileMapper.map(response);
    }

    public ServerResponse getProfileById(ServerRequest serverRequest) {
        log.info("getProfileById {}", serverRequest);
        String id = serverRequest.pathVariable("id");
        var request = ProfileRequest.newBuilder()
                .setId(Long.parseLong(id))
                .build();
        ProfileResponse response = profileClientGrpc.getProfileById(request);
        ProfileDto profileDto = profileMapper.convertToDto(response);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(profileDto);
    }

    public List<MobileProfileResponse> getAllProfiles(Empty request) {
        log.info("getAllProfiles");
        List<ProfileResponse> profiles = profileClientGrpc.getAllProfiles(request);
        log.info("profiles {}", profiles);
        return profiles.stream()
                .map(profileMapper::map)
                .toList();
    }

    public ServerResponse getAllProfiles(ServerRequest serverRequest) {
        log.info("getAllProfiles {}", serverRequest);
        List<ProfileDto> profiles = profileClientGrpc.getAllProfiles(Empty.newBuilder().build()).stream()
                .map(profileMapper::convertToDto)
                .toList();
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(profiles);
    }
}

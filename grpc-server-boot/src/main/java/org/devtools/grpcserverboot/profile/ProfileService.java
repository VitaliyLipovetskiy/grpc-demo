package org.devtools.grpcserverboot.profile;

import com.google.rpc.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devtools.grpc.ProfileRequest;
import org.devtools.grpc.ProfileResponse;
import org.devtools.grpcserverboot.mapper.ProfileMapper;
import org.devtools.grpcserverboot.validation.exceptions.ApplicationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileResponse getCurrentProfile(ProfileRequest request) {
        log.info("getCurrentProfile {}", request);
        Profile profile = profileRepository.getProfileById(request.getId()).orElseThrow(
                () -> new ApplicationException(Code.NOT_FOUND, "Profile not found")
        );
        return profileMapper.convertToResponse(profile);
    }

    public List<ProfileResponse> getAllProfiles() {
        log.info("getAllProfiles");
        return profileRepository.getAllProfiles().stream()
                .map(profileMapper::convertToResponse)
                .toList();
    }
}

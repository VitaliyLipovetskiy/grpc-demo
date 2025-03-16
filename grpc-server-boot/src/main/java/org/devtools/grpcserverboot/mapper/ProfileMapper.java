package org.devtools.grpcserverboot.mapper;

import org.devtools.grpc.ProfileResponse;
import org.devtools.grpcserverboot.config.MapperConfig;
import org.devtools.grpcserverboot.profile.Profile;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ProfileMapper {

    ProfileResponse convertToResponse(Profile profile);
}

package org.devtools.grpcserver.mapper;

import org.devtools.grpc.ProfileResponse;
import org.devtools.grpcserver.config.MapperConfig;
import org.devtools.grpcserver.profile.Profile;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ProfileMapper {

    ProfileResponse convertToResponse(Profile profile);
}

package org.devtools.grpcclient.mapper;

import org.devtools.grpc.ProfileResponse;
import org.devtools.grpc.mobile.MobileProfileResponse;
import org.devtools.grpcclient.config.MapperConfig;
import org.devtools.grpcclient.profile.dto.ProfileDto;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ProfileMapper {

    ProfileDto convertToDto(ProfileResponse response);

    default MobileProfileResponse map(ProfileResponse response) {
        var builder = MobileProfileResponse.newBuilder()
                .setId(response.getId())
                .setName(response.getName())
                .setEnabled(response.getEnabled());
        if (response.hasAddress()) {
            builder.setAddress(response.getAddress());
        }
        return builder.build();
    }
}

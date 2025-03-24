package org.devtools.grpcclientboot.profile.dto;

public record ProfileDto(
        Long id,
        String name,
        boolean enabled,
        String address
) {}

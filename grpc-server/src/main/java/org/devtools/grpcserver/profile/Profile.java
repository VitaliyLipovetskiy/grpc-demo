package org.devtools.grpcserver.profile;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Profile {
    private Long id;
    private String name;
    boolean enabled = true;
    String address;
}

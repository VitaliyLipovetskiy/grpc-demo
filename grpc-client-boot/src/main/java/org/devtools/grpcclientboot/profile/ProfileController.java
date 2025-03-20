package org.devtools.grpcclientboot.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.servlet.function.RequestPredicates.GET;
import org.springframework.web.servlet.function.RouterFunction;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class ProfileController {

    @Bean
    public RouterFunction<ServerResponse> profileRouter(ProfileService profileService) {
        return route(
                GET("/profiles/{id}"),
//                        .and(accept(MediaType.APPLICATION_JSON)),
                profileService::getProfileById
        ).and(route(
                GET("/profiles"),
                profileService::getAllProfiles)
        );
    }
}

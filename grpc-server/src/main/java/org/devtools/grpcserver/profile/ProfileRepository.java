package org.devtools.grpcserver.profile;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProfileRepository {
    private final Map<Long, Profile> profiles = new ConcurrentHashMap<>();

    public ProfileRepository() {
        profiles.put(1L, new Profile(1L, "Мій профіль 1", true, null));
        profiles.put(2L, new Profile(2L, "Мій профіль 2", false, "Адреса"));
    }

    public Optional<Profile> getProfileById(Long id) {
        if (!profiles.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.of(profiles.get(id));
    }

    public List<Profile> getAllProfiles() {
        return profiles.values().stream()
                .toList();
    }
}

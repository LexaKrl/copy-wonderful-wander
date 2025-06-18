package com.technokratos.client;

import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.dto.response.user.UserForPostResponse;
import com.technokratos.dto.response.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/{userId}/tech")
    UserForPostResponse getUsersById(@PathVariable UUID userId);

    @GetMapping("/api/users/{userId}/friends/tech")
    List<UUID> getFriendsByUserId(@PathVariable UUID userId);

}

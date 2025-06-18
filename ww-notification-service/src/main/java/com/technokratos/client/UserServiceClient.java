package com.technokratos.client;

import com.technokratos.dto.response.PageResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(
        name = "user-service-client",
        url = "${user-service.url}"
)
public interface UserServiceClient {
    @GetMapping("/api/users/{userId}/followers")
    PageResponse<UserCompactResponse> getFollowersByUserId(
            @PathVariable("userId") UUID userId,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size);
}

package com.technokratos.utils;

import com.technokratos.config.properties.ApplicationUrlProperties;
import com.technokratos.dto.response.PageResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestTemplateUtils {

    private final RestTemplate restTemplate;
    private final ApplicationUrlProperties applicationUrlProperties;

    public List<UUID> getFollowingByUserId(UUID userId) {
        String url = UriComponentsBuilder.fromUriString(applicationUrlProperties.getBaseUrl())
                .path(applicationUrlProperties.getSubscribersRetrieveUrl())
                .queryParam("page", 1)
                .queryParam("size", Integer.MAX_VALUE)
                .buildAndExpand(userId)
                .toUriString();

        ResponseEntity<PageResponse<UserCompactResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        return Objects.requireNonNull(response.getBody())
                .data()
                .stream()
                .map(UserCompactResponse::userId)
                .toList();
    }
}

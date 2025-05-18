package com.technokratos.controller;

import com.technokratos.dto.request.security.UserForJwtTokenRequest;
import com.technokratos.dto.request.user.UserRequest;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.enums.security.UserRole;
import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.service.auth.JWTService;
import com.technokratos.util.ApiEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(scripts = "/data/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/data/delete-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
public class UserControllerIntegrationTest {
    private HttpHeaders baseHttpHeaders;

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    JWTService jwtService;

    @BeforeEach
    void init() {
        final String jwtToken = jwtService.generateAccessToken(
                new UserForJwtTokenRequest(
                        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                        "john_doe"
                )
        );
        baseHttpHeaders = new HttpHeaders();
        baseHttpHeaders.setContentType(MediaType.APPLICATION_JSON);
        baseHttpHeaders.setBearerAuth(jwtToken);
    }

    @Test
    void getCurrentUserProfile_whenEverythingIsCorrect_thenReturnUserResponse() {
        final UUID expectedUserId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        final String expectedUsername = "john_doe";
        final String expectedEmail = "john.doe@example.com";
        final String expectedFirstname = "John";
        final String expectedLastname = "Doe";
        final String expectedBio = "Love hiking and photography!";
        final UserRole expectedRole = UserRole.ROLE_USER;
        final String expectedAvatarUrl = "https://example.com/avatars/john_doe.jpg";
        final int expectedFollowersCount = 150;
        final int expectedFollowingCount = 200;
        final int expectedFriendsCount = 180;

        final ResponseEntity<UserResponse> response = testRestTemplate.exchange(
                ApiEndpoint.UserController.GET_CURRENT_USER_PROFILE,
                HttpMethod.GET,
                new HttpEntity<>(baseHttpHeaders),
                UserResponse.class
        );

        final UserResponse actualUserResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualUserResponse).isNotNull();

        assertThat(actualUserResponse.userId()).isEqualTo(expectedUserId);
        assertThat(actualUserResponse.username()).isEqualTo(expectedUsername);
        assertThat(actualUserResponse.email()).isEqualTo(expectedEmail);
        assertThat(actualUserResponse.firstname()).isEqualTo(expectedFirstname);
        assertThat(actualUserResponse.lastname()).isEqualTo(expectedLastname);
        assertThat(actualUserResponse.bio()).isEqualTo(expectedBio);
        assertThat(actualUserResponse.role()).isEqualTo(expectedRole);
        assertThat(actualUserResponse.avatarUrl()).isEqualTo(expectedAvatarUrl);
        assertThat(actualUserResponse.followersCount()).isEqualTo(expectedFollowersCount);
        assertThat(actualUserResponse.followingCount()).isEqualTo(expectedFollowingCount);
        assertThat(actualUserResponse.friendsCount()).isEqualTo(expectedFriendsCount);
    }

    @Test
    void getCurrentUserProfile_whenJwtNotValid_thenReturnUnauthorized() {
        baseHttpHeaders.setBearerAuth("");
        final ResponseEntity<String> response = testRestTemplate.exchange(
                ApiEndpoint.UserController.GET_CURRENT_USER_PROFILE,
                HttpMethod.GET,
                new HttpEntity<>(baseHttpHeaders),
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void updateCurrentUser_whenEverythingIsCorrect_thenReturnUpdatedUserResponse() {
        final ResponseEntity<UserResponse> getCurrentUserProfileResponse = testRestTemplate.exchange(
                ApiEndpoint.UserController.GET_CURRENT_USER_PROFILE,
                HttpMethod.GET,
                new HttpEntity<>(baseHttpHeaders),
                UserResponse.class
        );

        final UserResponse expectedUserResponse = getCurrentUserProfileResponse.getBody();

        assertThat(getCurrentUserProfileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getCurrentUserProfileResponse.getBody()).isNotNull();

        final UserRequest UserRequestToUpdate = new UserRequest(
                "updated_email@example.com",
                "updated_firstname",
                expectedUserResponse.lastname(),
                expectedUserResponse.bio(),
                PhotoVisibility.PRIVATE,
                expectedUserResponse.walkVisibility());

        final ResponseEntity<UserResponse> updateCurrentUserResponse = testRestTemplate.exchange(
                ApiEndpoint.UserController.UPDATE_CURRENT_USER,
                HttpMethod.PUT,
                new HttpEntity<>(UserRequestToUpdate, baseHttpHeaders),
                UserResponse.class
        );

        final UserResponse actualUserResponse = updateCurrentUserResponse.getBody();

        assertThat(updateCurrentUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualUserResponse).isNotNull();

        /*
         * Check that the fields have actually changed
         * */
        assertThat(actualUserResponse.email()).isEqualTo(UserRequestToUpdate.email());
        assertThat(actualUserResponse.firstname()).isEqualTo(UserRequestToUpdate.firstname());
        assertThat(actualUserResponse.photoVisibility()).isEqualTo(UserRequestToUpdate.photoVisibility());

        /*
         * Check that the other fields have not changed
         * */
        assertThat(actualUserResponse.userId()).isEqualTo(expectedUserResponse.userId());
        assertThat(actualUserResponse.username()).isEqualTo(expectedUserResponse.username());
        assertThat(actualUserResponse.lastname()).isEqualTo(expectedUserResponse.lastname());
        assertThat(actualUserResponse.bio()).isEqualTo(expectedUserResponse.bio());
        assertThat(actualUserResponse.role()).isEqualTo(expectedUserResponse.role());
        assertThat(actualUserResponse.avatarUrl()).isEqualTo(expectedUserResponse.avatarUrl());
        assertThat(actualUserResponse.walkVisibility()).isEqualTo(expectedUserResponse.walkVisibility());
        assertThat(actualUserResponse.followersCount()).isEqualTo(expectedUserResponse.followersCount());
        assertThat(actualUserResponse.followingCount()).isEqualTo(expectedUserResponse.followingCount());
        assertThat(actualUserResponse.friendsCount()).isEqualTo(expectedUserResponse.friendsCount());
    }

    @Test
    void updateCurrentUser_whenUserRequestNotValid_thenReturnBadRequest() {
        final UserRequest userRequestToUpdate = new UserRequest(
                "updated_email@example.com",
                null,
                null,
                null,
                PhotoVisibility.FRIENDS_ONLY,
                null);

        final ResponseEntity<String> updateCurrentUserResponse = testRestTemplate.exchange(
                ApiEndpoint.UserController.UPDATE_CURRENT_USER,
                HttpMethod.PUT,
                new HttpEntity<>(userRequestToUpdate, baseHttpHeaders),
                String.class
        );

        assertThat(updateCurrentUserResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(updateCurrentUserResponse.getBody()).isNotNull();
    }

    @Test
    void deleteCurrentUser_whenEverythingIsCorrect_thenReturnNoContent() {
        final ResponseEntity<String> deleteCurrentUserResponse = testRestTemplate.exchange(
                ApiEndpoint.UserController.DELETE_CURRENT_USER,
                HttpMethod.DELETE,
                new HttpEntity<>(baseHttpHeaders),
                String.class
        );

        assertThat(deleteCurrentUserResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(deleteCurrentUserResponse.getBody()).isNull();
    }
}

package com.technokratos.controller;

import com.technokratos.dto.UserInfoForJwt;
import com.technokratos.dto.request.user.UserRequest;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.dto.response.user.UserProfileResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.enums.security.UserRole;
import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.service.auth.JwtService;
import com.technokratos.util.ApiEndpoint;
import com.technokratos.util.PathVariable;
import com.technokratos.util.QueryParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
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
    JwtService jwtService;

    @BeforeEach
    void init() {
        final String jwtToken = jwtService.generateAccessToken(
                new UserInfoForJwt(
                        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                        "john_doe",
                        UserRole.ROLE_USER
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
        final int expectedFollowersCount = 6;
        final int expectedFollowingCount = 5;
        final int expectedFriendsCount = 2;

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
        assertThat(expectedUserResponse).isNotNull();

        final UserRequest UserRequestToUpdate = new UserRequest(
                "updated_email@example.com",
                "updated_username",
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
                "up",
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

    @Test
    void follow_whenEverythingIsCorrect_thenReturnNoContentAndNumberOfFollowingIncreasedByOne() {
        final ResponseEntity<UserResponse> getExpectedCurrentUserProfileResponse = testRestTemplate.exchange(
                ApiEndpoint.UserController.GET_CURRENT_USER_PROFILE,
                HttpMethod.GET,
                new HttpEntity<>(baseHttpHeaders),
                UserResponse.class
        );

        final UserResponse expectedUserResponse = getExpectedCurrentUserProfileResponse.getBody();

        assertThat(getExpectedCurrentUserProfileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(expectedUserResponse).isNotNull();

        final UUID targetUserUuid = UUID.fromString("cafebabe-cafe-babe-cafe-babecafebabe");

        final ResponseEntity<String> followResponse = testRestTemplate.exchange(
                UriComponentsBuilder.fromUriString(ApiEndpoint.UserController.FOLLOW)
                        .buildAndExpand(Map.of(
                                PathVariable.UserController.TARGET_USER_ID, targetUserUuid)
                        )
                        .toUri(),
                HttpMethod.POST,
                new HttpEntity<>(baseHttpHeaders),
                String.class
        );

        assertThat(followResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(followResponse.getBody()).isNull();

        final ResponseEntity<UserResponse> getActualUserProfileResponse = testRestTemplate.exchange(
                ApiEndpoint.UserController.GET_CURRENT_USER_PROFILE,
                HttpMethod.GET,
                new HttpEntity<>(baseHttpHeaders),
                UserResponse.class
        );

        final UserResponse actualUserResponse = getActualUserProfileResponse.getBody();

        assertThat(getActualUserProfileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualUserResponse).isNotNull();

        assertThat(actualUserResponse.followingCount()).isEqualTo(expectedUserResponse.followingCount() + 1);
        assertThat(actualUserResponse.followersCount()).isEqualTo(expectedUserResponse.followersCount());
        assertThat(actualUserResponse.friendsCount()).isEqualTo(expectedUserResponse.friendsCount());
    }

    @Test
    void follow_whenUuidInPathVariableIsNotValid_thenReturnBadRequest() {
        final String notValidUuid = "afebabsdkfdsfse-cafe-babe-cafe-babecafebabe";

        final ResponseEntity<String> followResponse = testRestTemplate.exchange(
                UriComponentsBuilder.fromUriString(ApiEndpoint.UserController.FOLLOW)
                        .buildAndExpand(Map.of(
                                PathVariable.UserController.TARGET_USER_ID, notValidUuid)
                        )
                        .toUri(),
                HttpMethod.POST,
                new HttpEntity<>(baseHttpHeaders),
                String.class
        );

        assertThat(followResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(followResponse.getBody()).isNotNull();
    }

    @Test
    void follow_whenTargetUserWasNotFound_thenReturnNotFound() {
        final UUID randomUuid = UUID.randomUUID();

        final ResponseEntity<String> followResponse = testRestTemplate.exchange(
                UriComponentsBuilder.fromUriString(ApiEndpoint.UserController.FOLLOW)
                        .buildAndExpand(Map.of(
                                PathVariable.UserController.TARGET_USER_ID, randomUuid)
                        )
                        .toUri(),
                HttpMethod.POST,
                new HttpEntity<>(baseHttpHeaders),
                String.class
        );

        assertThat(followResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(followResponse.getBody()).isNotNull();
    }

    @Test
    void follow_whenAttemptToFollowToPersonWhoIsAlreadyFollowedTo_thenReturnConflict() {
        final UUID targetUserUuid = UUID.fromString("cafebabe-cafe-babe-cafe-babecafebabe");

        final ResponseEntity<String> firstFollowResponse = testRestTemplate.exchange(
                UriComponentsBuilder.fromUriString(ApiEndpoint.UserController.FOLLOW)
                        .buildAndExpand(Map.of(
                                PathVariable.UserController.TARGET_USER_ID, targetUserUuid)
                        )
                        .toUri(),
                HttpMethod.POST,
                new HttpEntity<>(baseHttpHeaders),
                String.class
        );

        assertThat(firstFollowResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(firstFollowResponse.getBody()).isNull();

        final ResponseEntity<String> secondFollowResponse = testRestTemplate.exchange(
                UriComponentsBuilder.fromUriString(ApiEndpoint.UserController.FOLLOW)
                        .buildAndExpand(Map.of(
                                PathVariable.UserController.TARGET_USER_ID, targetUserUuid)
                        )
                        .toUri(),
                HttpMethod.POST,
                new HttpEntity<>(baseHttpHeaders),
                String.class
        );

        assertThat(secondFollowResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(secondFollowResponse.getBody()).isNotNull();
    }

    @Test
    void unfollow_whenEverythingIsCorrect_thenReturnNoContentAndNumberOfFollowingReducedByOne() {
        /*
         * Getting the current values
         * */
        final ResponseEntity<UserResponse> getExpectedCurrentUserProfileResponse = testRestTemplate.exchange(
                ApiEndpoint.UserController.GET_CURRENT_USER_PROFILE,
                HttpMethod.GET,
                new HttpEntity<>(baseHttpHeaders),
                UserResponse.class
        );

        final UserResponse expectedUserResponse = getExpectedCurrentUserProfileResponse.getBody();

        assertThat(getExpectedCurrentUserProfileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(expectedUserResponse).isNotNull();

        final UUID targetUserUuid = UUID.fromString("cafebabe-cafe-babe-cafe-babecafebabe");

        /*
         * Follow to user with targetUserUuid
         * */
        final ResponseEntity<String> followResponse = testRestTemplate.exchange(
                UriComponentsBuilder.fromUriString(ApiEndpoint.UserController.FOLLOW)
                        .buildAndExpand(Map.of(
                                PathVariable.UserController.TARGET_USER_ID, targetUserUuid)
                        )
                        .toUri(),
                HttpMethod.POST,
                new HttpEntity<>(baseHttpHeaders),
                String.class
        );

        assertThat(followResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(followResponse.getBody()).isNull();

        /*
         * Unfollow to user with targetUserUuid
         * */
        final ResponseEntity<String> unfollowResponse = testRestTemplate.exchange(
                UriComponentsBuilder.fromUriString(ApiEndpoint.UserController.UNFOLLOW)
                        .buildAndExpand(Map.of(
                                PathVariable.UserController.TARGET_USER_ID, targetUserUuid)
                        )
                        .toUri(),
                HttpMethod.DELETE,
                new HttpEntity<>(baseHttpHeaders),
                String.class
        );

        assertThat(unfollowResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(unfollowResponse.getBody()).isNull();

        /*
         * Getting the final values
         * */
        final ResponseEntity<UserResponse> getActualUserProfileResponse = testRestTemplate.exchange(
                ApiEndpoint.UserController.GET_CURRENT_USER_PROFILE,
                HttpMethod.GET,
                new HttpEntity<>(baseHttpHeaders),
                UserResponse.class
        );

        final UserResponse actualUserResponse = getActualUserProfileResponse.getBody();

        assertThat(getActualUserProfileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualUserResponse).isNotNull();

        assertThat(actualUserResponse.followingCount()).isEqualTo(expectedUserResponse.followingCount());
        assertThat(actualUserResponse.followersCount()).isEqualTo(expectedUserResponse.followersCount());
        assertThat(actualUserResponse.friendsCount()).isEqualTo(expectedUserResponse.friendsCount());
    }

    @Test
    void unfollow_whenTargetUserWasNotFound_thenReturnNotFound() {
        final UUID randomUuid = UUID.randomUUID();

        final ResponseEntity<String> unfollowResponse = testRestTemplate.exchange(
                UriComponentsBuilder.fromUriString(ApiEndpoint.UserController.UNFOLLOW)
                        .buildAndExpand(Map.of(
                                PathVariable.UserController.TARGET_USER_ID, randomUuid)
                        )
                        .toUri(),
                HttpMethod.DELETE,
                new HttpEntity<>(baseHttpHeaders),
                String.class
        );

        assertThat(unfollowResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(unfollowResponse.getBody()).isNotNull();
    }

    @Test
    void getUserProfileById_whenEverythingIsCorrectAndMainTestAccountFollowedToIt_thenReturnOkAndUserProfileResponse() {
        final UUID expectedUserId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        final String expectedUsername = "user2";
        final String expectedFirstname = "Bob";
        final String expectedLastname = "Johnson";
        final String expectedBio = "Bio for user2";
        final String expectedAvatarUrl = "http://example.com/avatar2.jpg";
        final int expectedFollowersCount = 1;
        final int expectedFollowingCount = 0;
        final int expectedFriendsCount = 0;
        final boolean expectedIsFollowedByUser = true;
        final boolean expectedIsFollowingByUser = false;
        final boolean expectedIsFriends = false;

        final ResponseEntity<UserProfileResponse> getUserProfileByIdResponse = testRestTemplate.exchange(
                UriComponentsBuilder.fromUriString(ApiEndpoint.UserController.GET_USER_PROFILE_BY_ID)
                        .buildAndExpand(Map.of(
                                PathVariable.UserController.TARGET_USER_ID, expectedUserId)
                        )
                        .toUri(),
                HttpMethod.GET,
                new HttpEntity<>(baseHttpHeaders),
                UserProfileResponse.class
        );

        final UserProfileResponse actualUserProfileResponse = getUserProfileByIdResponse.getBody();

        assertThat(getUserProfileByIdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualUserProfileResponse).isNotNull();

        assertThat(actualUserProfileResponse.userId()).isEqualTo(expectedUserId);
        assertThat(actualUserProfileResponse.username()).isEqualTo(expectedUsername);
        assertThat(actualUserProfileResponse.firstname()).isEqualTo(expectedFirstname);
        assertThat(actualUserProfileResponse.lastname()).isEqualTo(expectedLastname);
        assertThat(actualUserProfileResponse.bio()).isEqualTo(expectedBio);
        assertThat(actualUserProfileResponse.avatarUrl()).isEqualTo(expectedAvatarUrl);
        assertThat(actualUserProfileResponse.followingCount()).isEqualTo(expectedFollowingCount);
        assertThat(actualUserProfileResponse.followersCount()).isEqualTo(expectedFollowersCount);
        assertThat(actualUserProfileResponse.friendsCount()).isEqualTo(expectedFriendsCount);
        assertThat(actualUserProfileResponse.isFollowedByUser()).isEqualTo(expectedIsFollowedByUser);
        assertThat(actualUserProfileResponse.isFollowingByUser()).isEqualTo(expectedIsFollowingByUser);
        assertThat(actualUserProfileResponse.isFriends()).isEqualTo(expectedIsFriends);
    }

    @Test
    void getFriendsByUserId_whenEverythingIsCorrect_thenReturnOkAndListWithUserCompactResponse() {
        final UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        final int page = 0;
        final int size = 2;

        final ResponseEntity<List<UserCompactResponse>> friendsResponse = testRestTemplate.exchange(
                UriComponentsBuilder.fromUriString(ApiEndpoint.UserController.GET_FRIENDS_BY_USER_ID)
                        .queryParam(QueryParameter.Pageable.PAGE, page)
                        .queryParam(QueryParameter.Pageable.SIZE, size)
                        .buildAndExpand(Map.of(
                                PathVariable.UserController.USER_ID, userId
                        ))
                        .toUri(),
                HttpMethod.GET,
                new HttpEntity<>(baseHttpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        List<UserCompactResponse> friends = friendsResponse.getBody();

        assertThat(friendsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(friends).isNotNull();

        assertThat(friends.size()).isEqualTo(2);
        assertThat(friends.get(0)).isNotNull();
        assertThat(friends.get(1)).isNotNull();
        assertThat(friends.get(0).userId()).isNotNull();
        assertThat(friends.get(0).username()).isNotNull();
        assertThat(friends.get(1).userId()).isNotNull();
        assertThat(friends.get(1).username()).isNotNull();
    }

    @Test
    void getFollowingByUserId_whenEverythingIsCorrect_thenReturnOkAndListWithUserCompactResponse() {
        final UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        final int page = 0;
        final int size = 2;

        final ResponseEntity<List<UserCompactResponse>> followingResponse = testRestTemplate.exchange(
                UriComponentsBuilder.fromUriString(ApiEndpoint.UserController.GET_FOLLOWING_BY_USER_ID)
                        .queryParam(QueryParameter.Pageable.PAGE, page)
                        .queryParam(QueryParameter.Pageable.SIZE, size)
                        .buildAndExpand(Map.of(
                                PathVariable.UserController.USER_ID, userId
                        ))
                        .toUri(),
                HttpMethod.GET,
                new HttpEntity<>(baseHttpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        List<UserCompactResponse> following = followingResponse.getBody();

        assertThat(followingResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(following).isNotNull();

        assertThat(following.size()).isEqualTo(2);
        assertThat(following.get(0)).isNotNull();
        assertThat(following.get(1)).isNotNull();
        assertThat(following.get(0).userId()).isNotNull();
        assertThat(following.get(0).username()).isNotNull();
        assertThat(following.get(1).userId()).isNotNull();
        assertThat(following.get(1).username()).isNotNull();
    }

    @Test
    void getFollowersByUserId_whenEverythingIsCorrect_thenReturnOkAndListWithUserCompactResponse() {
        final UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        final int page = 0;
        final int size = 2;

        final ResponseEntity<List<UserCompactResponse>> followersResponse = testRestTemplate.exchange(
                UriComponentsBuilder.fromUriString(ApiEndpoint.UserController.GET_FOLLOWERS_BY_USER_ID)
                        .queryParam(QueryParameter.Pageable.PAGE, page)
                        .queryParam(QueryParameter.Pageable.SIZE, size)
                        .buildAndExpand(Map.of(
                                PathVariable.UserController.USER_ID, userId
                        ))
                        .toUri(),
                HttpMethod.GET,
                new HttpEntity<>(baseHttpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        List<UserCompactResponse> followers = followersResponse.getBody();

        assertThat(followersResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(followers).isNotNull();

        assertThat(followers.size()).isEqualTo(2);
        assertThat(followers.get(0)).isNotNull();
        assertThat(followers.get(1)).isNotNull();
        assertThat(followers.get(0).userId()).isNotNull();
        assertThat(followers.get(0).username()).isNotNull();
        assertThat(followers.get(1).userId()).isNotNull();
        assertThat(followers.get(1).username()).isNotNull();
    }
}
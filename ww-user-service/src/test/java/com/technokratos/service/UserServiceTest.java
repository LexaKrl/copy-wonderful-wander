package com.technokratos.service;

import com.technokratos.dto.response.UserCompactResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.UUID;

@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testGetFriendsByUserId() {
        UUID userId = UUID.fromString("8431cd6b-3697-48b3-b63a-44980947b6f6");
        UUID targetUserId = UUID.fromString("f2a7b9c4-8e5d-41a3-bc7f-9e6d5c4a3b2f");
        List<UserCompactResponse> accountList = userService.getFriendsByUserId(userId, targetUserId);
        System.out.println(accountList);
    }

    @Test
    public void testGetFollowersByUserId() {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        List<UserCompactResponse> accountList = userService.getFollowersByUserId(userId);
        System.out.println(accountList);
    }

    @Test
    public void testGetFollowingByUserId() {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        List<UserCompactResponse> accountList = userService.getFollowingByUserId(userId);
        System.out.println(accountList);
    }

    @Test
    public void testSubscribe() {
        UUID userId = UUID.fromString("99999999-9999-9999-9999-999999999999");
        UUID targetUserId = UUID.fromString("77777777-7777-7777-7777-777777777777");
        userService.subscribe(userId, targetUserId);
    }

    @Test
    public void testUnsubscribe() {
        UUID userId = UUID.fromString("99999999-9999-9999-9999-999999999999");
        UUID targetUserId = UUID.fromString("77777777-7777-7777-7777-777777777777");
        userService.subscribe(userId, targetUserId);
        userService.unsubscribe(userId, targetUserId);
    }

    @Test
    public void testGetProfileByUserId() {
        UUID userId = UUID.fromString("99999999-9999-9999-9999-999999999999");
        System.out.println(userService.getProfileByUserId(userId));
    }
}

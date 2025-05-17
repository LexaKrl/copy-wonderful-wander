package com.technokratos.controller;

import com.technokratos.dto.request.security.UserForJwtTokenRequest;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.service.auth.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {
    private final TestRestTemplate testRestTemplate;
    private final JWTService jwtService;
    private final UUID test_user_id;
    private final String test_user_username;

    @LocalServerPort
    private int port;
    private String baseUrl;
    private String jwtToken;

    @Autowired
    public UserControllerIntegrationTest(TestRestTemplate testRestTemplate, JWTService jwtService) {
        this.testRestTemplate = testRestTemplate;
        this.jwtService = jwtService;
        this.test_user_id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        this.test_user_username = "test_user";
    }

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:%s".formatted(port);
        jwtToken = jwtService.generateAccessToken(
                new UserForJwtTokenRequest(test_user_id, test_user_username)
        );
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/sql/init-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
    })
    public void testGetCurrentUserProfile() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<UserResponse> response = testRestTemplate.exchange(
                baseUrl + "/api/users/me",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().userId()).isEqualTo(test_user_id);
        assertThat(response.getBody().username()).isEqualTo(test_user_username);
    }
}

package io.github.agajansahatov.utopia.api.unit.controllers;

import io.github.agajansahatov.utopia.api.config.SecurityConfig;
import io.github.agajansahatov.utopia.api.controllers.AuthController;
import io.github.agajansahatov.utopia.api.entities.User;
import io.github.agajansahatov.utopia.api.services.JwtTokenService;
import io.github.agajansahatov.utopia.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class, JwtTokenService.class})
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserService userService;

    private TestUtils testUtils;

    private User user;

    @BeforeEach
    void setUp() {
        testUtils = new TestUtils(mockMvc, passwordEncoder, userService);
        user = testUtils.getTestUser("customer");
        given(userService.loadUserByUsername(user.getContact())).willReturn(user);
    }

    @Test
    void authenticate_Success_Returns200AndJwtToken() throws Exception {
        String authRequest = testUtils.getAuthRequest(user.getContact(), testUtils.getPlainUserPassword());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequest))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8", result.getResponse().getContentType());
    }

    @Test
    void authenticate_WrongPassword_Returns401() throws Exception {
        String authRequest = testUtils.getAuthRequest(user.getContact(), "wrongPassword");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequest))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticate_WrongContact_Returns404() throws Exception {
        String authRequest = testUtils.getAuthRequest("wrongContact@email.com", testUtils.getPlainUserPassword());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequest))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticate_InvalidEmail_Returns400() throws Exception {
        String authRequest = testUtils.getAuthRequest("InvalidEmail", testUtils.getPlainUserPassword());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authenticate_InvalidPhoneNumber_Returns400() throws Exception {
        String authRequest = testUtils.getAuthRequest("+123-456-789", testUtils.getPlainUserPassword());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authenticate_EmptyContact_Returns400() throws Exception {
        String authRequest = testUtils.getAuthRequest("", testUtils.getPlainUserPassword());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authenticate_ShortContact_Returns400() throws Exception {
        String authRequest = testUtils.getAuthRequest("123", testUtils.getPlainUserPassword());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authenticate_LongContact_Returns400() throws Exception {
        String authRequest = testUtils.getAuthRequest(
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890",
                testUtils.getPlainUserPassword()
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authenticate_EmptyPassword_Returns400() throws Exception {
        String authRequest = testUtils.getAuthRequest(user.getContact(), "");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authenticate_ShortPassword_Returns400() throws Exception {
        String authRequest = testUtils.getAuthRequest(user.getContact(), "1234");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authenticate_LongPassword_Returns400() throws Exception {
        String authRequest = testUtils.getAuthRequest(
                user.getContact(),
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequest))
                .andExpect(status().isBadRequest());
    }
}
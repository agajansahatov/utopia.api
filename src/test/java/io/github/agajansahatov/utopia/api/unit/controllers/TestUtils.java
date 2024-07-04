package io.github.agajansahatov.utopia.api.unit.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.agajansahatov.utopia.api.entities.Role;
import io.github.agajansahatov.utopia.api.entities.User;
import io.github.agajansahatov.utopia.api.models.requestDTOs.AuthRequest;
import io.github.agajansahatov.utopia.api.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUtils {
    private final User user;
    private final Role role;
    private final String password = "123456";

    private final MockMvc mockMvc;

    private final UserService userService;

    public TestUtils(MockMvc mockMvc, PasswordEncoder passwordEncoder, UserService userService) {
        this.mockMvc = mockMvc;
        this.userService = userService;

        user = new User();
        user.setId(1L);
        user.setFirstname("Test");
        user.setLastname("User");
        user.setPassword(passwordEncoder.encode(password));
        user.setContact("testuser@email.com");

        role = new Role();
        role.setId((byte) 1);
        role.setName("owner");

        user.setRole(role);
    }

    public User getTestUser(String roleName) {
        if(!roleName.equals("owner") && !roleName.equals("admin") && !roleName.equals("customer")){
            throw new IllegalArgumentException("Invalid role name: " + roleName);
        }

        if (roleName.equals("owner")) {
            role.setId((byte) 1);
        }

        if (roleName.equals("admin")) {
            role.setId((byte) 2);
        }

        if (roleName.equals("customer")) {
            role.setId((byte) 3);
        }

        role.setName(roleName);
        user.setRole(role);

        return user;
    }

    public String getPlainUserPassword() {
        return password;
    }

    public String getAuthRequest(String contact, String password) throws JsonProcessingException {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setContact(contact);
        authRequest.setPassword(password);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(authRequest);
    }

    private String getTokenBySendingRequest() throws Exception {
        given(userService.loadUserByUsername(user.getContact())).willReturn(user);

        String authRequestAsJson = getAuthRequest(user.getContact(), getPlainUserPassword());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestAsJson))
                .andExpect(status().isOk())
                .andReturn();
        return result.getResponse().getContentAsString();
    }

    public String getJwtTokenAsOwner() throws Exception {
        role.setId((byte) 1);
        role.setName("owner");
        user.setRole(role);
        return getTokenBySendingRequest();
    }

    public String getJwtTokenAsAdmin() throws Exception {
        role.setId((byte) 2);
        role.setName("admin");
        user.setRole(role);
        return getTokenBySendingRequest();
    }

    public String getJwtTokenAsCustomer() throws Exception {
        role.setId((byte) 3);
        role.setName("customer");
        user.setRole(role);
        return getTokenBySendingRequest();
    }
}

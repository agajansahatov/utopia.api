package io.github.agajansahatov.utopia.api.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.agajansahatov.utopia.api.config.SecurityConfig;
import io.github.agajansahatov.utopia.api.controllers.AuthController;
import io.github.agajansahatov.utopia.api.controllers.ProductController;
import io.github.agajansahatov.utopia.api.entities.Role;
import io.github.agajansahatov.utopia.api.entities.User;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductDetailsForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductForAdminDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductSummaryForCustomerDTO;
import io.github.agajansahatov.utopia.api.services.JwtTokenService;
import io.github.agajansahatov.utopia.api.services.ProductService;
import io.github.agajansahatov.utopia.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({ProductController.class, AuthController.class})
@Import({SecurityConfig.class, JwtTokenService.class})
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ProductService productService;

    @MockBean
    UserService userService;


    @BeforeEach
    public void setUp() {
        given(productService.exists(1L)).willReturn(true);
    }

    @Test
    void getProduct_WhenUnauthenticated_ReturnsProductForCustomer() throws Exception {
        // Given
        ProductForCustomerDTO product = new ProductForCustomerDTO();
        product.setId(1L);
        product.setTitle("Sample Product");

        given(productService.getProduct(1L)).willReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"title\":\"Sample Product\"}"));
    }

    @Test
    @WithMockUser
    void getProduct_WhenAuthenticated_ReturnsProductWithPropertiesBasedOnUserRole() throws Exception {
        // Given User
        User user = new User();
        user.setId(1L);
        user.setFirstname("Test");
        user.setLastname("User");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setContact("testuser@email.com");
        Role role = new Role();
        role.setId((byte) 1);
        role.setName("owner");
        user.setRole(role);
        given(userService.loadUserByUsername(user.getContact())).willReturn(user);

        // Create AuthRequest
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("contact", user.getContact());
        requestBody.put("password", "123456");
        ObjectMapper objectMapper = new ObjectMapper();
        String authRequest = objectMapper.writeValueAsString(requestBody);

        // Get JwtToken
        MvcResult result = mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequest))
                .andExpect(status().isOk())
                .andReturn();

        String jwtToken = result.getResponse().getContentAsString();

        // Given Product
        ProductForAdminDTO product = new ProductForAdminDTO();
        product.setId(1L);
        product.setTitle("Sample Product");
        product.setOriginalPrice(BigDecimal.valueOf(10));
        product.setSalesPrice(BigDecimal.valueOf(15));

        given(productService.getProduct(1L)).willReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/1")
                        .header("x-auth-token", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(equalTo(product.getId().intValue()))))
                .andExpect(jsonPath("$.title", is(product.getTitle())));
    }

    @Test
    void getProduct_WithIncorrectAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/products/1")
                        .header("x-auth-token", "invalid_jwt_token")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isUnauthorized());
    }

    @Test
    void getProductById_WhenIdNotExists_Returns404() throws Exception {
        Long invalidId = 999L;

        given(productService.exists(invalidId)).willReturn(false);

        mockMvc.perform(get("/api/products/" + invalidId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProduct_WithIncorrectView_Returns400() throws Exception {
        mockMvc.perform(get("/api/products/1?view=blabla")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void getProduct_WithDefaultView_ReturnsProduct() throws Exception {
        // Given
        ProductForCustomerDTO product = new ProductForCustomerDTO();
        product.setId(1L);
        product.setTitle("Sample Product");

        given(productService.getProduct(1L)).willReturn(Optional.of(product));

        // When & Then
        mockMvc.perform(get("/api/products/1?view=default")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"title\":\"Sample Product\"}"));
    }

    @Test
    void getProduct_WithViewSummary_ReturnsProductSummary() throws Exception {
        // Given
        ProductSummaryForCustomerDTO product = new ProductSummaryForCustomerDTO();
        product.setId(1L);
        product.setTitle("Sample Product");

        given(productService.getProductSummary(1L)).willReturn(Optional.of(product));

        // When & Then
        mockMvc.perform(get("/api/products/1?view=summary")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"title\":\"Sample Product\"}"));
    }

    @Test
    void getProduct_WithViewDetails_ReturnsProductDetails() throws Exception {
        // Given
        ProductDetailsForCustomerDTO product = new ProductDetailsForCustomerDTO();
        product.setId(1L);
        product.setTitle("Sample Product");

        given(productService.getProductDetails(1L)).willReturn(Optional.of(product));

        // When & Then
        mockMvc.perform(get("/api/products/1?view=details")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"title\":\"Sample Product\"}"));
    }
}

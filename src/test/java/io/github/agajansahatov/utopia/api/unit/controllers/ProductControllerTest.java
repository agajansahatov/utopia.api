package io.github.agajansahatov.utopia.api.unit.controllers;

import io.github.agajansahatov.utopia.api.config.SecurityConfig;
import io.github.agajansahatov.utopia.api.controllers.AuthController;
import io.github.agajansahatov.utopia.api.controllers.ProductController;
import io.github.agajansahatov.utopia.api.entities.Product;
import io.github.agajansahatov.utopia.api.mappers.ProductMapper;
import io.github.agajansahatov.utopia.api.mappers.ProductMapperImpl;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductDetailsDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductSummaryDTO;
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

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({ProductController.class, AuthController.class})
@Import({SecurityConfig.class, JwtTokenService.class})
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenService jwtTokenService;

    private Product product;

    private String getProductUrl;

    private final ProductMapper productMapper = new ProductMapperImpl();

    @MockBean
    private ProductService productService;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setId(1L);
        product.setTitle("Sample Product");
        product.setOriginalPrice(BigDecimal.valueOf(10));
        product.setSalesPrice(BigDecimal.valueOf(15));

        given(productService.exists(product.getId())).willReturn(true);

        getProductUrl = String.format("/api/products/%d", product.getId());
    }

    @Test
    void getProduct_WhenUnauthenticated_ReturnsProductForCustomer() throws Exception {
        ProductDTO productDTO = productMapper.productToProductForCustomerDTO(product);
        given(productService.getProduct(product.getId())).willReturn(Optional.of(productDTO));

        mockMvc.perform(get(getProductUrl).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(equalTo(product.getId().intValue()))))
                .andExpect(jsonPath("$.title", is(product.getTitle())));
    }

    @Test
    @WithMockUser
    void getProduct_WhenAuthenticated_ReturnsProductWithPropertiesBasedOnUserRole() throws Exception {
        ProductDTO productDTO = productMapper.productToProductForAdminDTO(product);
        given(productService.getProduct(product.getId())).willReturn(Optional.of(productDTO));

        TestUtils testUtils = new TestUtils(mockMvc, passwordEncoder, userService);
        String jwtToken = testUtils.getJwtTokenAsOwner();

        mockMvc.perform(get(getProductUrl).header("x-auth-token", jwtToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(equalTo(product.getId().intValue()))))
                .andExpect(jsonPath("$.title", is(product.getTitle())))
                .andExpect(jsonPath("$.originalPrice", is(product.getOriginalPrice().intValue())))
                .andExpect(jsonPath("$.salesPrice", is(product.getSalesPrice().intValue())));
    }

    @Test
    void getProduct_WithIncorrectAuth_Returns401() throws Exception {
        mockMvc.perform(get(getProductUrl)
                        .header("x-auth-token", "invalid_jwt_token")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isUnauthorized());
    }

    @Test
    void getProduct_WhenIdNotExists_Returns404() throws Exception {
        Long invalidId = 999L;
        given(productService.exists(invalidId)).willReturn(false);

        mockMvc.perform(get(String.format("/api/products/%d", invalidId))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProduct_WithIncorrectView_Returns400() throws Exception {
        mockMvc.perform(get(String.format("%s?view=blabla", getProductUrl))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void getProduct_WithViewDefault_ReturnsProduct() throws Exception {
        ProductDTO productDTO = productMapper.productToProductForCustomerDTO(product);
        given(productService.getProduct(product.getId())).willReturn(Optional.of(productDTO));

        mockMvc.perform(get(String.format("%s?view=default", getProductUrl))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(equalTo(product.getId().intValue()))))
                .andExpect(jsonPath("$.title", is(product.getTitle())));
    }

    @Test
    void getProduct_WithViewSummary_ReturnsProductSummary() throws Exception {
        ProductSummaryDTO productSummaryDTO = productMapper.productToProductSummaryForCustomerDTO(product);
        given(productService.getProductSummary(product.getId())).willReturn(Optional.of(productSummaryDTO));

        mockMvc.perform(get(String.format("%s?view=summary", getProductUrl))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(equalTo(product.getId().intValue()))))
                .andExpect(jsonPath("$.title", is(product.getTitle())));
    }

    @Test
    void getProduct_WithViewDetails_ReturnsProductDetails() throws Exception {
        ProductDetailsDTO productDetailsDTO = productMapper.productToProductDetailsForCustomerDTO(product);
        given(productService.getProductDetails(product.getId())).willReturn(Optional.of(productDetailsDTO));

        mockMvc.perform(get(String.format("%s?view=details", getProductUrl))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(equalTo(product.getId().intValue()))))
                .andExpect(jsonPath("$.title", is(product.getTitle())));
    }
}

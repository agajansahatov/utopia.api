package io.github.agajansahatov.utopia.api.unit.controllers;

import io.github.agajansahatov.utopia.api.config.SecurityConfig;
import io.github.agajansahatov.utopia.api.controllers.ProductController;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductDetailsForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductSummaryForCustomerDTO;
import io.github.agajansahatov.utopia.api.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(SecurityConfig.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getProduct() throws Exception {
        // Given
        ProductForCustomerDTO product = new ProductForCustomerDTO();
        product.setId(1L);
        product.setTitle("Sample Product");

//        given(productService.getProduct(1L)).willReturn(Optional.of(product));

        // When & Then
        mockMvc.perform(get("/api/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"title\":\"Sample Product\"}"));
    }

    @Test
    void getProductByDefault() throws Exception {
        // Given
        ProductForCustomerDTO product = new ProductForCustomerDTO();
        product.setId(1L);
        product.setTitle("Sample Product");

//        given(productService.getProduct(1L)).willReturn(Optional.of(product));

        // When & Then
        mockMvc.perform(get("/api/products/1?view=default")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"title\":\"Sample Product\"}"));
    }

    @Test
    void getProductBySummary() throws Exception {
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
    void getProductByDetails() throws Exception {
        // Given
        ProductDetailsForCustomerDTO product = new ProductDetailsForCustomerDTO();
        product.setId(1L);
        product.setTitle("Sample Product");

//        given(productService.getProductDetails(1L)).willReturn(Optional<>);

        // When & Then
        mockMvc.perform(get("/api/products/1?view=details")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"title\":\"Sample Product\"}"));
    }
}

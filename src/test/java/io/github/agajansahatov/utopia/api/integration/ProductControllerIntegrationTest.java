package io.github.agajansahatov.utopia.api.integration;

import io.github.agajansahatov.utopia.api.controllers.ProductController;
import io.github.agajansahatov.utopia.api.entities.Product;
import io.github.agajansahatov.utopia.api.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private Long savedProductId;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();

        Product product = new Product();
        product.setTitle("Test Product");
        product.setOriginalPrice(BigDecimal.valueOf(10.00));
        product.setSalesPrice(BigDecimal.valueOf(15.00));
        product.setNumberInStock(100); // Ensure the number in stock is set
        product.setDate(new Date()); // Ensure the date field is populated
        product.setDescription("A sample product description");

        Product savedProduct = productRepository.save(product);

        // Verify the product is saved correctly and get its ID
        savedProductId = savedProduct.getId();
        System.out.println("Saved product ID: {}" + savedProductId);

        Optional<Product> retrievedProduct = productRepository.findById(savedProductId);
        assertThat(retrievedProduct).isPresent();
        assertThat(retrievedProduct.get().getTitle()).isEqualTo("Test Product");
    }

    @Test
    @Order(1)
    public void testGetProductDefaultView() throws Exception {
        mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + savedProductId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedProductId))
                .andExpect(jsonPath("$.title").value("Test Product"))
                .andExpect(jsonPath("$.price").value(15.00));
    }

    @Test
    @Order(2)
    public void testGetProductSummaryView() throws Exception {
        mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + savedProductId)
                        .param("view", "summary")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedProductId))
                .andExpect(jsonPath("$.title").value("Test Product"));
    }

    @Test
    @Order(3)
    public void testGetProductDetailsView() throws Exception {
        mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + savedProductId)
                        .param("view", "details")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedProductId))
                .andExpect(jsonPath("$.title").value("Test Product"));
    }

    @Test
    @Order(4)
    public void testGetProductNotFound() throws Exception {
        mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product is not found"));
    }

    @Test
    @Order(5)
    public void testGetProductBadRequest() throws Exception {
        mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + savedProductId)
                        .param("view", "invalid")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The view param can be unset, 'default', 'details', or 'summary'."));
    }
}

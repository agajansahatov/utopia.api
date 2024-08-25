package io.github.agajansahatov.utopia.api.integration;

import io.github.agajansahatov.utopia.api.controllers.ProductController;
import io.github.agajansahatov.utopia.api.entities.Media;
import io.github.agajansahatov.utopia.api.entities.Product;
import io.github.agajansahatov.utopia.api.entities.Role;
import io.github.agajansahatov.utopia.api.entities.User;
import io.github.agajansahatov.utopia.api.repositories.MediaRepository;
import io.github.agajansahatov.utopia.api.repositories.ProductRepository;
import io.github.agajansahatov.utopia.api.repositories.RoleRepository;
import io.github.agajansahatov.utopia.api.repositories.UserRepository;
import io.github.agajansahatov.utopia.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.util.DateUtil.now;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MediaRepository mediaRepository;

    Product product = new Product();
    Media media = new Media();


    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();

        product.setTitle("Test Product");
        product.setOriginalPrice(BigDecimal.valueOf(10.00));
        product.setSalesPrice(BigDecimal.valueOf(15.00));
        product.setNumberInStock(100); // Ensure the number in stock is set
        product.setDate(new Date()); // Ensure the date field is populated
        product.setDescription("A sample product description");
        product = productRepository.save(product);

        media.setName("test.jpg");
        media.setIsMain(true);
        media.setProduct(product);
        mediaRepository.save(media);

        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    private User generateUserBasedOnRole(String roleName) {
        // You will need to get the roles from the Role entity in the future
        User user = new User();
        Role role = new Role();
        role.setId((byte) 1);
        role.setName(roleName);
        role = roleRepository.save(role);

        user.setRole(role);
        user.setContact("testuser@email.com");
        user.setFirstname("Test");
        user.setLastname("User");
        user.setCountry("Country");
        user.setProvince("Province");
        user.setCity("City");
        user.setAddress("Detailed Address");
        user.setPassword("123456");
        user.setBalance(BigDecimal.valueOf(1000.00));
        String userPlainPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthTime(now());

        user = userRepository.save(user);
        user.setPassword(userPlainPassword);

        return user;
    }

    @Nested
    class GetProductTests {
        @Test
        public void testGetProduct_WithInvalidId_ReturnsNotFoundError() throws Exception {
            mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/999")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        public void testGetProduct_WithInvalidView_ReturnsBadRequestError() throws Exception {
            mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                            .param("view", "invalid")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void getProduct_WithIncorrectAuthAndHasUser_Returns401() throws Exception {
            User user = generateUserBasedOnRole("customer");

            mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                            .header("x-auth-token", "invalid_jwt_token")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void getProduct_WithIncorrectAuthAndNoUser_Returns401() throws Exception {
            mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                            .header("x-auth-token", "invalid_jwt_token")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        public void getProduct_WithoutView_ReturnsProduct() throws Exception {
            mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(product.getId()))
                    .andExpect(jsonPath("$.title").value(product.getTitle()))
                    .andExpect(jsonPath("$.price").value(product.getSalesPrice()));
        }

        @Nested
        class GetProductWithDefaultViewTests {
            @Test
            public void getProduct_WithDefaultView_WithoutAuth_ReturnsProductForCustomer() throws Exception {
                mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                                .param("view", "default")
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(product.getId()))
                        .andExpect(jsonPath("$.title").value(product.getTitle()))
                        .andExpect(jsonPath("$.price").value(product.getSalesPrice()));
            }

            @Test
            public void getProduct_WithDefaultView_AsCustomer_ReturnsProductForCustomer() throws Exception {
                User user = generateUserBasedOnRole("customer");
                String jwtToken = userService.generateToken(user.getContact(), user.getPassword(), true);

                mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                                .header("x-auth-token", jwtToken)
                                .param("view", "default")
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(product.getId()))
                        .andExpect(jsonPath("$.title").value(product.getTitle()))
                        .andExpect(jsonPath("$.price").value(product.getSalesPrice()));
            }

            @Test
            public void getProduct_withDefaultView_AsAdmin_ReturnsProductForAdmin() throws Exception {
                User user = generateUserBasedOnRole("admin");
                String jwtToken = userService.generateToken(user.getContact(), user.getPassword(), true);

                mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                                .header("x-auth-token", jwtToken)
                                .param("view", "default")
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(product.getId()))
                        .andExpect(jsonPath("$.title").value(product.getTitle()))
                        .andExpect(jsonPath("$.numberInStock").value(product.getNumberInStock()))
                        .andExpect(jsonPath("$.originalPrice").value(product.getOriginalPrice()))
                        .andExpect(jsonPath("$.salesPrice").value(product.getSalesPrice()));
            }

            @Test
            public void getProduct_withDefaultView_AsOwner_ReturnsProductForAdmin() throws Exception {
                User user = generateUserBasedOnRole("owner");
                String jwtToken = userService.generateToken(user.getContact(), user.getPassword(), true);

                mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                                .header("x-auth-token", jwtToken)
                                .param("view", "default")
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(product.getId()))
                        .andExpect(jsonPath("$.title").value(product.getTitle()))
                        .andExpect(jsonPath("$.numberInStock").value(product.getNumberInStock()))
                        .andExpect(jsonPath("$.originalPrice").value(product.getOriginalPrice()))
                        .andExpect(jsonPath("$.salesPrice").value(product.getSalesPrice()));
            }
        }

        @Nested
        class GetProductWithSummaryViewTests {
            @Test
            public void getProduct_WithSummaryView_WithoutAuth_ReturnsProductSummaryForCustomer() throws Exception {
                mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                                .param("view", "summary")
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(product.getId()))
                        .andExpect(jsonPath("$.title").value(product.getTitle()))
                        .andExpect(jsonPath("$.price").value(product.getSalesPrice()))
                        .andExpect(jsonPath("$.mainMedia").value(media.getName()));
            }

            @Test
            public void getProduct_WithSummaryView_AsCustomer_ReturnsProductSummaryForCustomer() throws Exception {
                User user = generateUserBasedOnRole("customer");
                String jwtToken = userService.generateToken(user.getContact(), user.getPassword(), true);

                mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                                .param("view", "summary")
                                .header("x-auth-token", jwtToken)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(product.getId()))
                        .andExpect(jsonPath("$.title").value(product.getTitle()))
                        .andExpect(jsonPath("$.price").value(product.getSalesPrice()))
                        .andExpect(jsonPath("$.mainMedia").value(media.getName()));
            }

            @Test
            public void getProduct_WithSummaryView_AsAdmin_ReturnsProductSummaryForAdmin() throws Exception {
                User user = generateUserBasedOnRole("admin");
                String jwtToken = userService.generateToken(user.getContact(), user.getPassword(), true);

                mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                                .param("view", "summary")
                                .header("x-auth-token", jwtToken)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(product.getId()))
                        .andExpect(jsonPath("$.title").value(product.getTitle()))
                        .andExpect(jsonPath("$.mainMedia").value(media.getName()))
                        .andExpect(jsonPath("$.numberInStock").value(product.getNumberInStock()))
                        .andExpect(jsonPath("$.originalPrice").value(product.getOriginalPrice()))
                        .andExpect(jsonPath("$.salesPrice").value(product.getSalesPrice()));
            }

            @Test
            public void getProduct_WithSummaryView_AsOwner_ReturnsProductSummaryForAdmin() throws Exception {
                User user = generateUserBasedOnRole("owner");
                String jwtToken = userService.generateToken(user.getContact(), user.getPassword(), true);

                mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                                .param("view", "summary")
                                .header("x-auth-token", jwtToken)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(product.getId()))
                        .andExpect(jsonPath("$.title").value(product.getTitle()))
                        .andExpect(jsonPath("$.mainMedia").value(media.getName()))
                        .andExpect(jsonPath("$.numberInStock").value(product.getNumberInStock()))
                        .andExpect(jsonPath("$.originalPrice").value(product.getOriginalPrice()))
                        .andExpect(jsonPath("$.salesPrice").value(product.getSalesPrice()));
            }
        }

        @Nested
        class GetProductWithDetailsViewTests {
            @Test
            public void getProduct_WithDetailsView_WithoutAuth_ReturnsProductDetailsForCustomer() throws Exception {
                mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                                .param("view", "details")
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(product.getId()))
                        .andExpect(jsonPath("$.title").value(product.getTitle()))
                        .andExpect(jsonPath("$.price").value(product.getSalesPrice()))
                        .andExpect(jsonPath("$.likesCount").value(0));
            }

            @Test
            public void getProduct_WithDetailsView_AsCustomer_ReturnsProductDetailsForCustomer() throws Exception {
                User user = generateUserBasedOnRole("customer");
                String jwtToken = userService.generateToken(user.getContact(), user.getPassword(), true);

                mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                                .param("view", "details")
                                .header("x-auth-token", jwtToken)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(product.getId()))
                        .andExpect(jsonPath("$.title").value(product.getTitle()))
                        .andExpect(jsonPath("$.price").value(product.getSalesPrice()))
                        .andExpect(jsonPath("$.likesCount").value(0));
            }

            @Test
            public void getProduct_WithDetailsView_AsAdmin_ReturnsProductDetailsForAdmin() throws Exception {
                User user = generateUserBasedOnRole("admin");
                String jwtToken = userService.generateToken(user.getContact(), user.getPassword(), true);

                mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                                .param("view", "details")
                                .header("x-auth-token", jwtToken)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(product.getId()))
                        .andExpect(jsonPath("$.title").value(product.getTitle()))
                        .andExpect(jsonPath("$.numberInStock").value(product.getNumberInStock()))
                        .andExpect(jsonPath("$.originalPrice").value(product.getOriginalPrice()))
                        .andExpect(jsonPath("$.salesPrice").value(product.getSalesPrice()))
                        .andExpect(jsonPath("$.likesCount").value(0));
            }

            @Test
            public void getProduct_WithDetailsView_AsOwner_ReturnsProductDetailsForAdmin() throws Exception {
                User user = generateUserBasedOnRole("owner");
                String jwtToken = userService.generateToken(user.getContact(), user.getPassword(), true);

                mockMvc.perform(get(ProductController.ENDPOINT_PATH + "/" + product.getId())
                                .param("view", "details")
                                .header("x-auth-token", jwtToken)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(product.getId()))
                        .andExpect(jsonPath("$.title").value(product.getTitle()))
                        .andExpect(jsonPath("$.numberInStock").value(product.getNumberInStock()))
                        .andExpect(jsonPath("$.originalPrice").value(product.getOriginalPrice()))
                        .andExpect(jsonPath("$.salesPrice").value(product.getSalesPrice()))
                        .andExpect(jsonPath("$.likesCount").value(0));
            }
        }
    }
}

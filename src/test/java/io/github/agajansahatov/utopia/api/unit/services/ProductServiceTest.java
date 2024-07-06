package io.github.agajansahatov.utopia.api.unit.services;

import io.github.agajansahatov.utopia.api.config.RolesConfig;
import io.github.agajansahatov.utopia.api.entities.Product;
import io.github.agajansahatov.utopia.api.mappers.ProductMapper;
import io.github.agajansahatov.utopia.api.mappers.ProductMapperImpl;
import io.github.agajansahatov.utopia.api.models.ProductDetailsProjection;
import io.github.agajansahatov.utopia.api.models.ProductSummaryProjection;
import io.github.agajansahatov.utopia.api.models.responseDTOs.*;
import io.github.agajansahatov.utopia.api.repositories.ProductRepository;
import io.github.agajansahatov.utopia.api.services.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private final ProductMapper productMapper = new ProductMapperImpl();

    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, productMapper);

        product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");
        product.setOriginalPrice(BigDecimal.valueOf(10.00));
        product.setSalesPrice(BigDecimal.valueOf(15.00));
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void mockAuthentication(String role) {
        GrantedAuthority authority = () -> role;
        User user = new User("user", "password", Collections.singleton(authority));
        TestingAuthenticationToken authentication = new TestingAuthenticationToken(user, "password", Collections.singleton(authority));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Nested
    class ExistsTests {
        @Test
        void exists_ReturnsTrue() {
            // Given
            given(productRepository.existsById(product.getId())).willReturn(true);
            // When
            Boolean result = productService.exists(product.getId());
            // Then
            verify(productRepository).existsById(product.getId());
            assertEquals(true, result);
        }

        @Test
        void exists_ReturnsFalse() {
            // Given
            Long nonExistentProductId = 999L;
            given(productRepository.existsById(nonExistentProductId)).willReturn(false);

            // When
            Boolean result = productService.exists(nonExistentProductId);

            // Then
            verify(productRepository).existsById(nonExistentProductId);
            assertEquals(false, result);
        }
    }

    @Nested
    class GetProductTests {
        @Test
        void getProduct_WithoutAuth_ReturnsProductForCustomer() {
            // Given
            given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
            // When
            Optional<ProductDTO> result = productService.getProduct(product.getId());
            // Then
            verify(productRepository).findById(product.getId());
            ProductForCustomerDTO expected = productMapper.productToProductForCustomerDTO(product);
            assertEquals(Optional.of(expected), result);
        }

        @Test
        void getProduct_AsCustomer_ReturnsProductForCustomer() {
            // Given
            mockAuthentication(RolesConfig.ROLE_CUSTOMER);
            given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
            // When
            Optional<ProductDTO> result = productService.getProduct(product.getId());
            // Then
            verify(productRepository).findById(product.getId());
            ProductForCustomerDTO expected = productMapper.productToProductForCustomerDTO(product);
            assertEquals(Optional.of(expected), result);
        }

        @Test
        void getProduct_AsAdmin_ReturnsProductForAdmin() {
            // Given
            mockAuthentication(RolesConfig.ROLE_ADMIN);
            given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
            // When
            Optional<ProductDTO> result = productService.getProduct(product.getId());
            // Then
            verify(productRepository).findById(product.getId());
            ProductForAdminDTO expected = productMapper.productToProductForAdminDTO(product);
            assertEquals(Optional.of(expected), result);
        }

        @Test
        void getProduct_AsOwner_ReturnsProductForAdmin() {
            // Given
            mockAuthentication(RolesConfig.ROLE_OWNER);
            given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
            // When
            Optional<ProductDTO> result = productService.getProduct(product.getId());
            // Then
            verify(productRepository).findById(product.getId());
            ProductForAdminDTO expected = productMapper.productToProductForAdminDTO(product);
            assertEquals(Optional.of(expected), result);
        }
    }

    @Nested
    class GetProductDetailsTests {
        @Test
        void getProductDetails_withoutAuth_ReturnsProductDetailsForCustomer() {
            // Given
            Long productId = product.getId();
            ProductDetailsProjection projection = mock(ProductDetailsProjection.class);
            given(productRepository.findProductDetailsById(productId)).willReturn(Optional.of(projection));
            // When
            Optional<ProductDetailsDTO> result = productService.getProductDetails(productId);
            // Then
            verify(productRepository).findProductDetailsById(productId);
            ProductDetailsForCustomerDTO expected = productMapper.projectionToProductDetailsForCustomerDTO(projection);
            assertEquals(Optional.of(expected), result);
        }

        @Test
        void getProductDetails_AsCustomer_ReturnsProductDetailsForCustomer() {
            // Given
            mockAuthentication(RolesConfig.ROLE_CUSTOMER);
            Long productId = product.getId();
            ProductDetailsProjection projection = mock(ProductDetailsProjection.class);
            given(productRepository.findProductDetailsById(productId)).willReturn(Optional.of(projection));
            // When
            Optional<ProductDetailsDTO> result = productService.getProductDetails(productId);
            // Then
            verify(productRepository).findProductDetailsById(productId);
            ProductDetailsForCustomerDTO expected = productMapper.projectionToProductDetailsForCustomerDTO(projection);
            assertEquals(Optional.of(expected), result);
        }

        @Test
        void getProductDetails_AsAdmin_ReturnsProductDetailsForAdmin() {
            // Given
            mockAuthentication(RolesConfig.ROLE_ADMIN);
            Long productId = product.getId();
            ProductDetailsProjection projection = mock(ProductDetailsProjection.class);
            given(productRepository.findProductDetailsById(productId)).willReturn(Optional.of(projection));
            // When
            Optional<ProductDetailsDTO> result = productService.getProductDetails(productId);
            // Then
            verify(productRepository).findProductDetailsById(productId);
            ProductDetailsForAdminDTO expected = productMapper.projectionToProductDetailsForAdminDTO(projection);
            assertEquals(Optional.of(expected), result);
        }

        @Test
        void getProductDetails_AsOwner_ReturnsProductDetailsForAdmin() {
            // Given
            mockAuthentication(RolesConfig.ROLE_OWNER);
            Long productId = product.getId();
            ProductDetailsProjection projection = mock(ProductDetailsProjection.class);
            given(productRepository.findProductDetailsById(productId)).willReturn(Optional.of(projection));
            // When
            Optional<ProductDetailsDTO> result = productService.getProductDetails(productId);
            // Then
            verify(productRepository).findProductDetailsById(productId);
            ProductDetailsForAdminDTO expected = productMapper.projectionToProductDetailsForAdminDTO(projection);
            assertEquals(Optional.of(expected), result);
        }
    }

    @Nested
    class GetProductSummaryTests {
        @Test
        void getProductSummary_withoutAuth_ReturnsProductSummaryForCustomer() {
            // Given
            Long productId = product.getId();
            ProductSummaryProjection projection = mock(ProductSummaryProjection.class);
            given(productRepository.findProductSummaryById(productId)).willReturn(Optional.of(projection));
            // When
            Optional<ProductSummaryDTO> result = productService.getProductSummary(productId);
            // Then
            verify(productRepository).findProductSummaryById(productId);
            ProductSummaryForCustomerDTO expected = productMapper.projectionToProductSummaryForCustomerDTO(projection);
            assertEquals(Optional.of(expected), result);
        }

        @Test
        void getProductSummary_AsCustomer_ReturnsProductSummaryForCustomer() {
            // Given
            mockAuthentication(RolesConfig.ROLE_CUSTOMER);
            Long productId = product.getId();
            ProductSummaryProjection projection = mock(ProductSummaryProjection.class);
            given(productRepository.findProductSummaryById(productId)).willReturn(Optional.of(projection));
            // When
            Optional<ProductSummaryDTO> result = productService.getProductSummary(productId);
            // Then
            verify(productRepository).findProductSummaryById(productId);
            ProductSummaryForCustomerDTO expected = productMapper.projectionToProductSummaryForCustomerDTO(projection);
            assertEquals(Optional.of(expected), result);
        }

        @Test
        void getProductSummary_AsAdmin_ReturnsProductSummaryForAdmin() {
            // Given
            mockAuthentication(RolesConfig.ROLE_ADMIN);
            Long productId = product.getId();
            ProductSummaryProjection projection = mock(ProductSummaryProjection.class);
            given(productRepository.findProductSummaryById(productId)).willReturn(Optional.of(projection));
            // When
            Optional<ProductSummaryDTO> result = productService.getProductSummary(productId);
            // Then
            verify(productRepository).findProductSummaryById(productId);
            ProductSummaryForAdminDTO expected = productMapper.projectionToProductSummaryForAdminDTO(projection);
            assertEquals(Optional.of(expected), result);
        }

        @Test
        void getProductSummary_AsOwner_ReturnsProductSummaryForAdmin() {
            // Given
            mockAuthentication(RolesConfig.ROLE_OWNER);
            Long productId = product.getId();
            ProductSummaryProjection projection = mock(ProductSummaryProjection.class);
            given(productRepository.findProductSummaryById(productId)).willReturn(Optional.of(projection));
            // When
            Optional<ProductSummaryDTO> result = productService.getProductSummary(productId);
            // Then
            verify(productRepository).findProductSummaryById(productId);
            ProductSummaryForAdminDTO expected = productMapper.projectionToProductSummaryForAdminDTO(projection);
            assertEquals(Optional.of(expected), result);
        }
    }
}

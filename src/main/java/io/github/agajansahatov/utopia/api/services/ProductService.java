package io.github.agajansahatov.utopia.api.services;

import io.github.agajansahatov.utopia.api.entities.Product;
import io.github.agajansahatov.utopia.api.mappers.ProductMapper;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductDetailsDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductSummaryDTO;
import io.github.agajansahatov.utopia.api.models.ProductDetailsProjection;
import io.github.agajansahatov.utopia.api.models.ProductSummaryProjection;
import io.github.agajansahatov.utopia.api.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.github.agajansahatov.utopia.api.config.RolesConfig.*;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Optional<ProductDTO> getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        String role = getCurrentAuthRole();
        if(role.equalsIgnoreCase(ROLE_OWNER) || role.equalsIgnoreCase(ROLE_ADMIN)){
            return product.map(productMapper::productToProductForAdminDTO);
        }

        return product.map(productMapper::productToProductForCustomerDTO);
    }

    public Optional<ProductDetailsDTO> getProductDetails(Long id) {
        Optional<ProductDetailsProjection> projection = productRepository.findProductDetailsById(id);

        String role = getCurrentAuthRole();
        if(role.equalsIgnoreCase(ROLE_OWNER) || role.equalsIgnoreCase(ROLE_ADMIN)){
            return projection.map(productMapper::projectionToProductDetailsForAdminDTO);
        }
        return projection.map(productMapper::projectionToProductDetailsForCustomerDTO);
    }

    public Optional<ProductSummaryDTO> getProductSummary(Long id) {
        Optional<ProductSummaryProjection> projection = productRepository.findProductSummaryById(id);

        String role = getCurrentAuthRole();
        if(role.equalsIgnoreCase(ROLE_OWNER) || role.equalsIgnoreCase(ROLE_ADMIN)){
            return projection.map(productMapper::projectionToProductSummaryForAdminDTO);
        }

        return projection.map(productMapper::projectionToProductSummaryForCustomerDTO);
    }

    public boolean exists(Long id) {
        return productRepository.existsById(id);
    }
}

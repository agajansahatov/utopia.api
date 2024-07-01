package io.github.agajansahatov.utopia.api.services;

import io.github.agajansahatov.utopia.api.mappers.ProductMapper;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductSummaryForCustomerDTO;
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

    public Optional<ProductForCustomerDTO> getProduct(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productToProductForCustomerDTO);
    }

    public Optional<?> getProductDetails(Long id) {
        String role = getCurrentAuthRole();
        log.debug("Role: {}", role);

        if(role.equalsIgnoreCase(ROLE_OWNER) || role.equalsIgnoreCase(ROLE_ADMIN)){
            Optional<ProductDetailsProjection> projection = productRepository.findProductDetailsById(id);
            return projection.map(productMapper::projectionToProductDetailsForAdminAndCustomerDTO);
        }
        Optional<ProductDetailsProjection> projection = productRepository.findProductDetailsById(id);
        return projection.map(productMapper::projectionToProductDetailsForCustomerDTO);
    }

    public Optional<ProductSummaryForCustomerDTO> getProductSummary(Long id) {
        Optional<ProductSummaryProjection> projection = productRepository.findProductSummaryById(id);
        return projection.map(productMapper::projectionToProductSummaryForCustomerDTO);
    }
}

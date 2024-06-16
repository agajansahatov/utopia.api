package io.github.agajansahatov.utopia.api.services;

import io.github.agajansahatov.utopia.api.mappers.ProductMapper;
import io.github.agajansahatov.utopia.api.models.ProductDetailsForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.ProductForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.ProductSummaryForCustomerDTO;
import io.github.agajansahatov.utopia.api.projections.ProductDetailsProjection;
import io.github.agajansahatov.utopia.api.projections.ProductSummaryProjection;
import io.github.agajansahatov.utopia.api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductServiceOptimizedImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceOptimizedImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // Same with ProductServiceEagerImpl
    @Override
    public Optional<ProductForCustomerDTO> getProduct(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productToProductForCustomerDTO);
    }

    @Override
    public Optional<ProductDetailsForCustomerDTO> getProductDetails(Long id) {
        Optional<ProductDetailsProjection> projection = productRepository.findProductDetailsById(id);
        return projection.map(productMapper::projectionToProductDetailsForCustomerDTO);
    }

    @Override
    public Optional<ProductSummaryForCustomerDTO> getProductSummary(Long id) {
        Optional<ProductSummaryProjection> projection = productRepository.findProductSummaryById(id);
        return projection.map(productMapper::projectionToProductSummaryForCustomerDTO);
    }
}

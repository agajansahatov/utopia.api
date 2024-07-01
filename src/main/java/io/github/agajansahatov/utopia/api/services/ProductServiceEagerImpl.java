package io.github.agajansahatov.utopia.api.services;

import io.github.agajansahatov.utopia.api.mappers.ProductMapper;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductDetailsForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductSummaryForCustomerDTO;
import io.github.agajansahatov.utopia.api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductServiceEagerImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceEagerImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // Same with ProductServiceOptimizedImpl
    @Override
    public Optional<ProductForCustomerDTO> getProduct(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productToProductForCustomerDTO);
    }

    @Override
    @NonNull
    public Optional<ProductDetailsForCustomerDTO> getProductDetails(Long id) {
        return productRepository.findWithAllDetailsById(id)
                .map(productMapper::productToProductDetailsForCustomerDTO);
    }

    @Override
    @Transactional
    public Optional<ProductSummaryForCustomerDTO> getProductSummary(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productToProductSummaryForCustomerDTO);
    }
}

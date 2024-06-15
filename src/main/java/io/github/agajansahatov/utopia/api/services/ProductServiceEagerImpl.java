package io.github.agajansahatov.utopia.api.services;

import io.github.agajansahatov.utopia.api.mappers.ProductMapper;
import io.github.agajansahatov.utopia.api.models.ProductDetailsForCustomerDTO;
import io.github.agajansahatov.utopia.api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
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

    @Override
    @NonNull
    public Optional<ProductDetailsForCustomerDTO> getProductForCustomer(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productToProductDetailsForCustomerDTO);
    }
}

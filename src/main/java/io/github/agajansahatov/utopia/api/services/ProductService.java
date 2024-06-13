package io.github.agajansahatov.utopia.api.services;

import io.github.agajansahatov.utopia.api.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProducts();
    Optional<Product> findProductById(Long id);
    Product saveProduct(Product product);
    void deleteProduct(Long id);
}

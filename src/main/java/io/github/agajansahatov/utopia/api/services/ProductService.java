package io.github.agajansahatov.utopia.api.services;

import io.github.agajansahatov.utopia.api.models.ProductDetailsForCustomerDTO;
import java.util.Optional;

public interface ProductService {
    Optional<ProductDetailsForCustomerDTO> getProductForCustomer(Long id);
}

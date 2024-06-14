package io.github.agajansahatov.utopia.api.services;

import io.github.agajansahatov.utopia.api.models.ProductDetailsForCustomerDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductService {
    Optional<ProductDetailsForCustomerDTO> getProductForCustomer(Long id);
}

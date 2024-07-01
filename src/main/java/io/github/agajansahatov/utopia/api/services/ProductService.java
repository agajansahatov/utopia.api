package io.github.agajansahatov.utopia.api.services;

import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductDetailsForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.responseDTOs.ProductSummaryForCustomerDTO;

import java.util.Optional;

public interface ProductService {
    Optional<ProductForCustomerDTO> getProduct(Long id);
    Optional<ProductDetailsForCustomerDTO> getProductDetails(Long id);
    Optional<ProductSummaryForCustomerDTO> getProductSummary(Long id);
}

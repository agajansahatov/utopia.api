package io.github.agajansahatov.utopia.api.models.responseDTOs;

public sealed interface ProductDetails permits ProductDetailsForAdminDTO, ProductDetailsForCustomerDTO {
}

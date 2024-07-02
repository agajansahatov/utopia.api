package io.github.agajansahatov.utopia.api.models.responseDTOs;

public sealed interface ProductDetailsDTO permits ProductDetailsForAdminDTO, ProductDetailsForCustomerDTO {
}

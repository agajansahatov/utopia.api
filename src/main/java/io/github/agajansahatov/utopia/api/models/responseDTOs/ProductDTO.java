package io.github.agajansahatov.utopia.api.models.responseDTOs;


public sealed interface ProductDTO permits ProductForAdminDTO, ProductForCustomerDTO {
}

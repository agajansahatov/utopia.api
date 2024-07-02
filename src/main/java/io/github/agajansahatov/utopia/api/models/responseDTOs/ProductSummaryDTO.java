package io.github.agajansahatov.utopia.api.models.responseDTOs;

public sealed interface ProductSummaryDTO permits ProductSummaryForAdminDTO, ProductSummaryForCustomerDTO {
}

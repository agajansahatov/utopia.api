package io.github.agajansahatov.utopia.api.models.responseDTOs;

public sealed interface ProductSummary permits ProductSummaryForAdminDTO, ProductSummaryForCustomerDTO {
}

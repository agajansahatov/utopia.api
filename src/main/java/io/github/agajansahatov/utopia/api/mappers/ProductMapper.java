package io.github.agajansahatov.utopia.api.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.agajansahatov.utopia.api.entities.Product;
import io.github.agajansahatov.utopia.api.models.responseDTOs.*;
import io.github.agajansahatov.utopia.api.models.ProductDetailsProjection;
import io.github.agajansahatov.utopia.api.models.ProductSummaryProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ObjectMapper objectMapper = new ObjectMapper();

    @Named("convertListToJson")
    static String convertListToJson(Object list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert list to JSON", e);
        }
    }

    @Mapping(target = "price", source = "salesPrice")
    ProductForCustomerDTO productToProductForCustomerDTO(Product p);

    @Mapping(target = "price", source = "salesPrice")
    ProductDetailsForCustomerDTO projectionToProductDetailsForCustomerDTO(ProductDetailsProjection p);
    ProductDetailsForAdminDTO projectionToProductDetailsForAdminDTO(ProductDetailsProjection p);

    @Mapping(target = "price", source = "salesPrice")
    ProductSummaryForCustomerDTO projectionToProductSummaryForCustomerDTO(ProductSummaryProjection p);
    ProductSummaryForAdminDTO projectionToProductSummaryForAdminDTO(ProductSummaryProjection p);
}

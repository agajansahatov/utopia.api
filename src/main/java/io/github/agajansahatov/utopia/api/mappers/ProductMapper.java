package io.github.agajansahatov.utopia.api.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.agajansahatov.utopia.api.entities.Product;
import io.github.agajansahatov.utopia.api.models.ProductDetailsForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.ProductSummaryForCustomerDTO;
import io.github.agajansahatov.utopia.api.projections.ProductDetailsProjection;
import io.github.agajansahatov.utopia.api.projections.ProductSummaryProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ObjectMapper objectMapper = new ObjectMapper();

    @Mapping(target = "price", source = "salesPrice")
    @Mapping(target = "likesCount", expression = "java(product.getLikedByUsers().size())")
    @Mapping(target = "visitsCount", expression = "java(product.getVisitedByUsers().size())")
    @Mapping(target = "ordersCount", expression = "java(product.getOrders().size())")
    @Mapping(target = "commentsCount", expression = "java(product.getComments().size())")
    @Mapping(target = "medias", source = "medias", qualifiedByName = "convertListToJson")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "convertListToJson")
    ProductDetailsForCustomerDTO productToProductDetailsForCustomerDTO(Product product);


    @Named("convertListToJson")
    static String convertListToJson(Object list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert list to JSON", e);
        }
    }

    @Mapping(target = "price", source = "salesPrice")
    ProductDetailsForCustomerDTO projectionToProductDetailsForCustomerDTO(ProductDetailsProjection projection);

    @Mapping(target = "price", source = "salesPrice")
    ProductSummaryForCustomerDTO projectionToProductSummaryForCustomerDTO(ProductSummaryProjection projection);
}

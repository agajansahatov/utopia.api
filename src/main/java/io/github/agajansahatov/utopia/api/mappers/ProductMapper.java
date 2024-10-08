package io.github.agajansahatov.utopia.api.mappers;

import io.github.agajansahatov.utopia.api.entities.Product;
import io.github.agajansahatov.utopia.api.models.ProductDetailsProjection;
import io.github.agajansahatov.utopia.api.models.ProductSummaryProjection;
import io.github.agajansahatov.utopia.api.models.responseDTOs.*;
import io.github.agajansahatov.utopia.api.entities.Media;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    @Mapping(target = "likesCount", expression = "java(product.getLikedByUsers().size())")
    @Mapping(target = "visitsCount", expression = "java(product.getVisitedByUsers().size())")
    @Mapping(target = "ordersCount", expression = "java(product.getOrders().size())")
    @Mapping(target = "commentsCount", expression = "java(product.getComments().size())")
    @Mapping(target = "medias", source = "medias", qualifiedByName = "convertListToJson")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "convertListToJson")
    ProductDetailsForCustomerDTO productToProductDetailsForCustomerDTO(Product product);

    @Named("getMainMediaName")
    static String getMainMediaName(Product product) {
        return product.getMedias().stream()
                .filter(Media::getIsMain)
                .findFirst()
                .map(Media::getName)
                .orElse(null);
    }
    @Mapping(target = "price", source = "salesPrice")
    @Mapping(target = "mainMedia", source = "product", qualifiedByName = "getMainMediaName")
    ProductSummaryForCustomerDTO productToProductSummaryForCustomerDTO(Product product);

    @Mapping(target = "price", source = "salesPrice")
    ProductForCustomerDTO productToProductForCustomerDTO(Product p);
    ProductForAdminDTO productToProductForAdminDTO(Product p);

    @Mapping(target = "price", source = "salesPrice")
    ProductDetailsForCustomerDTO projectionToProductDetailsForCustomerDTO(ProductDetailsProjection p);
    ProductDetailsForAdminDTO projectionToProductDetailsForAdminDTO(ProductDetailsProjection p);

    @Mapping(target = "price", source = "salesPrice")
    ProductSummaryForCustomerDTO projectionToProductSummaryForCustomerDTO(ProductSummaryProjection p);
    ProductSummaryForAdminDTO projectionToProductSummaryForAdminDTO(ProductSummaryProjection p);
}

package io.github.agajansahatov.utopia.api.mappers;

import io.github.agajansahatov.utopia.api.entities.Product;
import io.github.agajansahatov.utopia.api.models.ProductDetailsForCustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "price", source = "salesPrice")
    @Mapping(target = "likesCount", expression = "java(product.getLikedByUsers().size())")
    @Mapping(target = "visitsCount", expression = "java(product.getVisitedByUsers().size())")
    @Mapping(target = "ordersCount", expression = "java(product.getOrders().size())")
    @Mapping(target = "commentsCount", expression = "java(product.getComments().size())")
    ProductDetailsForCustomerDTO productToProductDetailsForCustomerDTO(Product product);
}

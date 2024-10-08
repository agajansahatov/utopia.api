package io.github.agajansahatov.utopia.api.models.responseDTOs;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
public final class ProductDetailsForAdminDTO implements ProductDetailsDTO {
    private Long id;
    private String title;
    private BigDecimal originalPrice;
    private BigDecimal salesPrice;
    private String description;
    private Date date;
    @JsonRawValue
    private String properties;
    @JsonRawValue
    private String medias;
    @JsonRawValue
    private String categories;
    private int numberInStock;
    private int likesCount;
    private int visitsCount;
    private int ordersCount;
    private int commentsCount;
}

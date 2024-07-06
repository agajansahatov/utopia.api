package io.github.agajansahatov.utopia.api.models.responseDTOs;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
public final class ProductForAdminDTO implements ProductDTO {
    private Long id;
    private String title;
    private BigDecimal originalPrice;
    private BigDecimal salesPrice;
    private int numberInStock;
    private String description;
    private Date date;
    @JsonRawValue
    private String properties;
}

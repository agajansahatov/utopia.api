package io.github.agajansahatov.utopia.api.models.responseDTOs;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
public final class ProductForCustomerDTO implements ProductDTO {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Date date;
    @JsonRawValue
    private String properties;
}

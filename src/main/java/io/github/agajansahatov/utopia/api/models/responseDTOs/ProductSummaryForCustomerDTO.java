package io.github.agajansahatov.utopia.api.models.responseDTOs;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
public final class ProductSummaryForCustomerDTO implements ProductSummaryDTO {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Date date;
    private String mainMedia;
}

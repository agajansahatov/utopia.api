package io.github.agajansahatov.utopia.api.models.responseDTOs;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public final class ProductSummaryForAdminDTO implements ProductSummaryDTO {
    private Long id;
    private String title;
    private BigDecimal originalPrice;
    private BigDecimal salesPrice;
    private int numberInStock;
    private String description;
    private Date date;
    private String mainMedia;
}

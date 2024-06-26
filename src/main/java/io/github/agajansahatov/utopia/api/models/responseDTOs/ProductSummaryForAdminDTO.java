package io.github.agajansahatov.utopia.api.models.responseDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

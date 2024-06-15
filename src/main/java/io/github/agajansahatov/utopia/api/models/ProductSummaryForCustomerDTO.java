package io.github.agajansahatov.utopia.api.models;

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
public class ProductSummaryForCustomerDTO {
    private Long id;
    private String title;
    private BigDecimal salesPrice;
    private String description;
    private Date date;
    private String mainMedia;
}
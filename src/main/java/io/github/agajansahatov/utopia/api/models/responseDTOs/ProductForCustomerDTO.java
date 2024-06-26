package io.github.agajansahatov.utopia.api.models.responseDTOs;

import com.fasterxml.jackson.annotation.JsonRawValue;
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
public final class ProductForCustomerDTO implements ProductDTO {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Date date;
    @JsonRawValue
    private String properties;
}

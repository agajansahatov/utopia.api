package io.github.agajansahatov.utopia.api.models;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.github.agajansahatov.utopia.api.entities.Category;
import io.github.agajansahatov.utopia.api.entities.Media;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsForCustomerDTO {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Date date;
    @JsonRawValue
    private String properties;
    private List<Media> medias;
    private List<Category> categories;
    private int numberInStock;
    private int likesCount;
    private int visitsCount;
    private int ordersCount;
    private int commentsCount;
}

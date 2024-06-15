package io.github.agajansahatov.utopia.api.projections;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.github.agajansahatov.utopia.api.entities.Category;
import io.github.agajansahatov.utopia.api.entities.Media;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ProductDetailsProjection {
    Long getId();
    String getTitle();
    BigDecimal getOriginalPrice();
    BigDecimal getSalesPrice();
    String getDescription();
    Date getDate();
    String getProperties();
    String getMedias();
    String getCategories();
    int getNumberInStock();
    int getLikesCount();
    int getVisitsCount();
    int getOrdersCount();
    int getCommentsCount();
}

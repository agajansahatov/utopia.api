package io.github.agajansahatov.utopia.api.models;

import java.math.BigDecimal;
import java.util.Date;

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

package io.github.agajansahatov.utopia.api.models;

import java.math.BigDecimal;
import java.util.Date;

public interface ProductSummaryProjection {
    Long getId();
    String getTitle();
    BigDecimal getOriginalPrice();
    BigDecimal getSalesPrice();
    int getNumberInStock();
    String getDescription();
    Date getDate();
    String getMainMedia();
}

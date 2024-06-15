package io.github.agajansahatov.utopia.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false)
    private BigDecimal originalPrice;

    @Column(nullable = false)
    private BigDecimal salesPrice;

    @Column(nullable = false)
    private int numberInStock;

    @Column
    private String description;

    @Column(nullable = false)
    private Date date;

    @Column(columnDefinition = "json")
    private String properties;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "categorized_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @JsonIgnore
    @ManyToMany(mappedBy = "likedProducts")
    private List<User> likedByUsers;

    @JsonIgnore
    @ManyToMany(mappedBy = "visitedProducts")
    private List<User> visitedByUsers;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Media> medias;
}

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

    @Column
    private String description;

    @Column(nullable = false)
    private Date date;

    @Column(columnDefinition = "json")
    private String properties;

    @ManyToMany
    @JoinTable(
            name = "categorized_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    private List<Category> categories;

    @ManyToMany(mappedBy = "likedProducts")
    @JsonIgnore
    private List<User> likedByUsers;

    @ManyToMany(mappedBy = "visitedProducts")
    @JsonIgnore
    private List<User> visitedByUsers;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Order> orders;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Media> medias;
}

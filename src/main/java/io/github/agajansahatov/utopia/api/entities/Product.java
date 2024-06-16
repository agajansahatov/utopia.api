package io.github.agajansahatov.utopia.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Category> categories = new LinkedHashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "likedProducts")
    private Set<User> likedByUsers = new LinkedHashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "visitedProducts")
    private Set<User> visitedByUsers = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Order> orders = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Comment> comments = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Media> medias = new LinkedHashSet<>();
}

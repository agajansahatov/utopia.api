package io.github.agajansahatov.utopia.api.repositories;

import io.github.agajansahatov.utopia.api.entities.Product;
import io.github.agajansahatov.utopia.api.projections.ProductDetailsProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @NonNull
    @EntityGraph(attributePaths = {"medias", "categories", "likedByUsers", "visitedByUsers", "orders", "comments"})
    Optional<Product> findById(@NonNull Long id);

    @NonNull
    @Query(value = "SELECT * FROM products_details_view WHERE id = ?1", nativeQuery = true)
    Optional<ProductDetailsProjection> findProductDetailsById(@NonNull Long id);
}
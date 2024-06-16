package io.github.agajansahatov.utopia.api.repositories;

import io.github.agajansahatov.utopia.api.entities.Product;
import io.github.agajansahatov.utopia.api.models.ProductDetailsForCustomerDTO;
import io.github.agajansahatov.utopia.api.projections.ProductDetailsProjection;
import io.github.agajansahatov.utopia.api.projections.ProductSummaryProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @NonNull
    @EntityGraph(attributePaths = {"medias", "categories", "likedByUsers", "visitedByUsers", "orders", "comments"})
    Optional<Product> findById(@NonNull Long id);

    @Query(value = "SELECT * FROM products_details_view WHERE id = :id", nativeQuery = true)
    Optional<ProductDetailsProjection> findProductDetailsById(@Param("id") Long id);

    @Query(value = "SELECT * FROM products_summary_view WHERE id = :id", nativeQuery = true)
    Optional<ProductSummaryProjection> findProductSummaryById(@Param("id") Long id);
}
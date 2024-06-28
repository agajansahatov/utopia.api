package io.github.agajansahatov.utopia.api.controllers;


import io.github.agajansahatov.utopia.api.models.ProductDetailsForCustomerDTO;
import io.github.agajansahatov.utopia.api.models.ProductSummaryForCustomerDTO;
import io.github.agajansahatov.utopia.api.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id, @RequestParam(required = false, defaultValue = "default") String view) {
        if(id == null)
            return ResponseEntity.badRequest()
                    .body("Product id cannot be null.");
        // Check if the id exists

        if (view.equalsIgnoreCase("default")) {
            return productService.getProduct(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        if (view.equalsIgnoreCase("summary")) {
            return productService.getProductSummary(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        if (view.equalsIgnoreCase("details")){
            return productService.getProductDetails(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        // In case if the view is not equal to default, summary, or details
        return ResponseEntity.badRequest()
                .body("The view param can be unset, 'default', 'details', or 'summary'.");
    }
}

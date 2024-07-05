package io.github.agajansahatov.utopia.api.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.github.agajansahatov.utopia.api.services.ProductService;

@RequiredArgsConstructor
@RestController
@RequestMapping(ProductController.ENDPOINT_PATH)
public class ProductController {
    public static final String ENDPOINT_PATH = "/api/products";

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id, @RequestParam(required = false, defaultValue = "default") String view) {
        if (!productService.exists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product is not found");
        }

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

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("The view param can be unset, 'default', 'details', or 'summary'.");
    }
}

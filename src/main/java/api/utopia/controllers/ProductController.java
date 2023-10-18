package api.utopia.controllers;

import api.utopia.entities.Product;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;


public class ProductController {
    private List<Product> products;
    private final JdbcTemplate jdbcTemplate;

    //Constructor
    public ProductController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        String query = "SELECT * FROM products";
        this.products = jdbcTemplate.query(query, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setImage(rs.getString("image"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getString("price"));
            product.setDescription(rs.getString("description"));
            product.setCategory(rs.getString("category"));
            product.setPopularity(rs.getString("popularity"));
            product.setDate(rs.getString("date"));
            return product;
        });
    }

    public Product addProduct(Product product){
        String query = "INSERT INTO products (image, name, price, description, category, popularity, date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(query,
                product.getImage(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory(),
                product.getPopularity(),
                product.getDate()
        );

        if (rowsAffected > 0){
            product.setId(products.size() + 1);
            products.add(product);
            return product;
        }

        return null;
    }


    public List<Product> getProducts() {
        List<Product> allProducts = new ArrayList<>();

        for (Product product : products) {
            // Edit the desired properties of each product
            String description = product.getDescription();
            if (description.length() > 51) {
                description = description.substring(0, 51);
            }
            product.setDescription(description);

            // Add the modified product to the allProducts list
            allProducts.add(product);
        }

        return allProducts;
    }



    public Product getProduct(long id){
        for (Product product: products) {
            if(product.getId() == id){
                return product;
            }
        }
        return null;
    }

    public long getSize() {
        return products.size();
    }
}

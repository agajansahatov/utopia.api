package com.utopia.api.controllers;

import com.utopia.api.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class MainController {
    private final UserController users;
    private final ProductController products;
    private final PurchasedProductsController purchasedProducts;

    private final FavouritesController favouritesController;
    private final VisitedController visitedController;
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    public MainController(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        this.users = new UserController(jdbcTemplate);
        this.products = new ProductController(jdbcTemplate);
        this.purchasedProducts = new PurchasedProductsController(jdbcTemplate);
        this.favouritesController = new FavouritesController(jdbcTemplate);
        this.visitedController = new VisitedController(jdbcTemplate);
    }

    private User userResponse(User user){
        if (user == null) {
            throw new IllegalArgumentException();
        }
        return user;
    }
    //Login
    @PostMapping("/auth")
    public User authenticate (@RequestBody User user) {
        return userResponse(users.exists(user.getContact(), user.getPassword()));
    }

    //Registration
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody User newUser) {
        return userResponse(users.addUser(newUser));
    }

    //Updating a user
    @PutMapping("/users")
    public User editUser(@RequestBody User user) {

        return userResponse(users.updateUser(user));
    }


    //Controlling Products
    private Product productResponse(Product product){
        if (product == null) {
            throw new IllegalArgumentException();
        }
        return product;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        List<Product> productList = products.getProducts();
        if(!productList.isEmpty()) {
            return productList;
        }
        throw new IllegalArgumentException();
    }

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable("productId") long id) {
        return products.getProduct(id);
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addNewProduct(@ModelAttribute Product newProduct,
                                 @RequestParam("file") MultipartFile file) {
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileName = "p" + (products.getSize() + 1) + "." + fileExtension;
            String filePath = System.getProperty("user.dir") + "/public/images/products/" + fileName;
            newProduct.setImage(fileName);

            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                throw new IllegalArgumentException("Error while transferring the file.");
            }

            return productResponse(products.addProduct(newProduct));
        } else {
            throw new IllegalArgumentException("No file uploaded.");
        }
    }

    //Controlling Purchased Products

    //Get purchasedProducts of a user
    @PostMapping("/products/purchased/all")
    public List<PurchasedProduct> getUserPurchasedProducts(@RequestBody User user) {
        List<PurchasedProduct> p = purchasedProducts.getProducts(user.getId());
        if(!p.isEmpty()) {
            return p;
        }
        return null;
    }


    @PostMapping("/products/purchased/new")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean addNewPurchasedProducts(@RequestBody List<PurchasedProduct> pProducts) {

        if (pProducts.isEmpty()){
            return false;
        }

        //Check up;
        long sum = 0;
        for (PurchasedProduct p : pProducts) {
            if(p != null) {
                User user = users.getUser(p.getUser());
                Product product = products.getProduct(p.getProduct());
                sum += Integer.parseInt(product.getPrice()) * p.getQuantity();
                if(sum > Integer.parseInt(user.getBalance())){
                    return false;
                }
            }

        }

        //Operations.
        for (PurchasedProduct p : pProducts) {
            if(p != null) {
                purchasedProducts.addNewProduct(p);

                //Updating the user balance
                User user = users.getUser(p.getUser());
                Product product = products.getProduct(p.getProduct());
                long price = Integer.parseInt(product.getPrice()) * p.getQuantity();
                long balance = Integer.parseInt(user.getBalance());
                user.setBalance((balance - price) + "");

                User response = users.updateUser(user);
                if(response == null) {
                    return false;
                }
            }

        }

        return true;
    }

    //Controlling favourites
    @PostMapping("/favourites")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean add(@RequestBody FavouriteProduct favouriteProduct) {
        favouritesController.add(favouriteProduct);
        return true;
    }

    @DeleteMapping("/favourites")
    public boolean remove(@RequestBody FavouriteProduct favouriteProduct) {
        favouritesController.remove(favouriteProduct);
        return true;
    }

    @GetMapping("/favourites/{userId}")
    public List<FavouriteProduct> getFavouriteProducts(@PathVariable("userId") int userId) {
        return favouritesController.getAll(userId);
    }

    @GetMapping("/favourites/count/{productId}")
    public long getProductFavCount(@PathVariable("productId") long id) {
        return favouritesController.getCountOfProduct(id);
    }



    //Controlling visitedProducts
    @PostMapping("/visited")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean add(@RequestBody VisitedProduct visitedProduct) {
        if(visitedController.isExists(visitedProduct)){
            visitedController.remove(visitedProduct);
        }
        visitedController.add(visitedProduct);
        return true;
    }

    @DeleteMapping("/visited")
    public boolean remove(@RequestBody VisitedProduct visitedProduct) {
        visitedController.remove(visitedProduct);
        return true;
    }

    @GetMapping("/visited/{userId}")
    public List<VisitedProduct> getVisitedProducts(@PathVariable("userId") long userId) {
        return visitedController.getAll(userId);
    }

    @GetMapping("/visited/count/{productId}")
    public long getProductVisitedCount(@PathVariable("productId") long id) {
        return visitedController.getCountOfProduct(id);
    }
}

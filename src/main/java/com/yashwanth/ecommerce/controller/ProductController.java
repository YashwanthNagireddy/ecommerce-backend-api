package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.entity.Product;
import com.yashwanth.ecommerce.service.ProductService;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService productService;


    public ProductController(ProductService productService) {

        this.productService = productService;

    }



    // ADD PRODUCT
    @PostMapping
    public Product addProduct(
            @RequestBody Product product
    ) {

        return productService.addProduct(product);

    }




    // GET ALL PRODUCTS
    @GetMapping
    public List<Product> getProducts() {

        return productService.getAllProducts();

    }




    // SEARCH PRODUCT
    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam String name
    ) {

        return productService.searchProducts(name);

    }




    // GET PRODUCT BY ID
    @GetMapping("/id/{id}")
    public Product getProduct(
            @PathVariable Long id
    ) {

        return productService.getProductById(id);

    }




    // UPDATE PRODUCT
    @PutMapping("/id/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product product
    ) {

        return productService.updateProduct(
                id,
                product
        );

    }




    // DELETE PRODUCT
    @DeleteMapping("/id/{id}")
    public String deleteProduct(
            @PathVariable Long id
    ) {

        productService.deleteProduct(id);

        return "Product deleted successfully";

    }

}
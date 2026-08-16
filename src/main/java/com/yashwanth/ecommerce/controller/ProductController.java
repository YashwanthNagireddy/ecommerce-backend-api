package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.entity.Product;
import com.yashwanth.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET ALL PRODUCTS
    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {

        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }

    // SEARCH PRODUCTS
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam String name
    ) {

        return ResponseEntity.ok(
                productService.searchProducts(name)
        );
    }

    // GET PRODUCT BY ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Product> getProduct(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }

    // CREATE PRODUCT
    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestBody Product product
    ) {

        return ResponseEntity.ok(
                productService.addProduct(product)
        );
    }

    // UPDATE PRODUCT
    @PutMapping("/id/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product
    ) {

        return ResponseEntity.ok(
                productService.updateProduct(id, product)
        );
    }

    // DELETE PRODUCT
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id
    ) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                "Product deleted successfully"
        );
    }
}
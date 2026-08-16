package com.yashwanth.ecommerce.service;

import com.yashwanth.ecommerce.entity.Product;
import com.yashwanth.ecommerce.exception.ResourceNotFoundException;
import com.yashwanth.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // CREATE PRODUCT
    public Product addProduct(Product product) {

        validateProduct(product);

        return productRepository.save(product);
    }

    // GET ALL PRODUCTS
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    // GET PRODUCT BY ID
    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );
    }

    // SEARCH PRODUCT
    public List<Product> searchProducts(String name) {

        return productRepository
                .findByNameContainingIgnoreCase(name);
    }

    // UPDATE PRODUCT
    public Product updateProduct(
            Long id,
            Product updatedProduct
    ) {

        validateProduct(updatedProduct);

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setQuantity(updatedProduct.getQuantity());

        return productRepository.save(product);
    }

    // DELETE PRODUCT
    public void deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Product not found with id: " + id
            );
        }

        productRepository.deleteById(id);
    }

    // VALIDATE PRODUCT
    private void validateProduct(Product product) {

        if (product.getName() == null ||
                product.getName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Product name is required"
            );
        }

        if (product.getPrice() < 0) {

            throw new RuntimeException(
                    "Product price cannot be negative"
            );
        }

        if (product.getQuantity() < 0) {

            throw new RuntimeException(
                    "Product quantity cannot be negative"
            );
        }
    }
}
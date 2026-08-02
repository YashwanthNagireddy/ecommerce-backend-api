package com.yashwanth.ecommerce.service;

import com.yashwanth.ecommerce.entity.Product;
import com.yashwanth.ecommerce.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {


    private final ProductRepository productRepository;



    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;

    }





    public Product addProduct(Product product) {

        return productRepository.save(product);

    }





    public List<Product> getAllProducts() {

        return productRepository.findAll();

    }





    public Product getProductById(Long id) {


        return productRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Product not found")
                );

    }





    public Product updateProduct(
            Long id,
            Product updatedProduct
    ) {


        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Product not found")
                );


        product.setName(updatedProduct.getName());

        product.setDescription(updatedProduct.getDescription());

        product.setPrice(updatedProduct.getPrice());

        product.setQuantity(updatedProduct.getQuantity());


        return productRepository.save(product);

    }





    public void deleteProduct(Long id) {

        productRepository.deleteById(id);

    }





    // SEARCH PRODUCT
    public List<Product> searchProducts(String name) {


        return productRepository.findByNameContainingIgnoreCase(name);

    }

}
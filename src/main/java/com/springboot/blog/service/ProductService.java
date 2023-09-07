package com.springboot.blog.service;

import com.springboot.blog.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    List<Product> searchProducts(String query);

    Product createProduct(Product product);
}

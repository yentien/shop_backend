package com.arthurtien.backend.service;

import com.arthurtien.backend.model.Product;

import java.util.List;

public interface ProductService {

    void deleteProduct(Integer productId);
    void modifyProduct(Integer productId,Product product);
    Integer createProduct(Product product);
    List<Product> getProducts();
    Product getProductById(Integer productId);
}

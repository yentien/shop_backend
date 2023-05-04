package com.arthurtien.backend.dao;

import com.arthurtien.backend.model.Product;

import java.util.List;

public interface ProductDao {

    void deleteProduct(Integer productId);
    void modifyProduct(Integer productId,Product product);
    Integer createProduct(Product product);
    List<Product> getProducts();
    Product getProductById(Integer productId);
}

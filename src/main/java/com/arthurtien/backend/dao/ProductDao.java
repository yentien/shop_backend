package com.arthurtien.backend.dao;

import com.arthurtien.backend.model.Product;

import java.util.List;

public interface ProductDao {
//    Product getProductId(Integer productId);

    List<Product> getProducts();

    Product getProductById(Integer productId);
}

package com.arthurtien.backend.service;

import com.arthurtien.backend.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();

    Product getProductById(Integer productId);
}

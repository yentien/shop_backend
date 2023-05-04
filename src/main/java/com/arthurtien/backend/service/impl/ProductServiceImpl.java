package com.arthurtien.backend.service.impl;

import com.arthurtien.backend.dao.ProductDao;
import com.arthurtien.backend.model.Product;
import com.arthurtien.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }

    @Override
    public void modifyProduct(Integer productId, Product product) {
        productDao.modifyProduct(productId, product);
    }

    @Override
    public Integer createProduct(Product product) {
        return productDao.createProduct(product);
    }

    @Override
    public List<Product> getProducts() {
        return productDao.getProducts();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}

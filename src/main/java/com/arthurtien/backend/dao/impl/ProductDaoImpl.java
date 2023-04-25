package com.arthurtien.backend.dao.impl;

import com.arthurtien.backend.dao.ProductDao;
import com.arthurtien.backend.model.Product;
import com.arthurtien.backend.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    @Override
//    public Product getProductId(Integer productId) {
//        return null;
//    }


    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, product_name, category," +
            " image_url, price, stock, description, created_date," +
            " last_modified_date" +
            " FROM product " +
            "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Product> getProducts() {
        String sql = "SELECT product_id, product_name, category," +
                " image_url, price, stock, description, created_date," +
                " last_modified_date " +
                "FROM product;";

        Map<String, Object> map = new HashMap<>();

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map , new ProductRowMapper());

        return productList;
    }
}

package com.arthurtien.backend.dao.impl;

import com.arthurtien.backend.dao.ProductDao;
import com.arthurtien.backend.model.Product;
import com.arthurtien.backend.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product" +
            " WHERE product_id = :productId;";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void modifyProduct(Integer productId, Product product) {
        String sql = "UPDATE product SET product_name = :productName," +
            " category = :category, image_url = :imageUrl, price = :price," +
            " stock = :stock, description = :description" +
            " WHERE product_id = :productId;";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", product.getProductName());
        map.put("category",product.getCategory().toString());
        map.put("imageUrl",product.getImageUrl());
        map.put("price",product.getPrice());
        map.put("stock",product.getStock());
        map.put("description",product.getDescription());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public Integer createProduct(Product product) {
        String sql = "INSERT INTO product (product_name," +
            " category, image_url, price, stock, description," +
            " created_date, last_modified_date)" +
            " VALUES (:productName, :category, :imageUrl," +
            " :price, :stock, :description, :createdDate," +
            " :lastModifiedDate);";
        Map<String, Object> map = new HashMap<>();
        map.put("productName", product.getProductName());
        map.put("category",product.getCategory().toString());
        map.put("imageUrl",product.getImageUrl());
        map.put("price",product.getPrice());
        map.put("stock",product.getStock());
        map.put("description",product.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        Integer productId = keyHolder.getKey().intValue();

        return productId;
    }

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

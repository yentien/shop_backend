package com.arthurtien.backend.rowmapper;

import com.arthurtien.backend.model.CartProduct;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartProductRowMapper implements RowMapper<CartProduct> {
  @Override
  public CartProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
    CartProduct cartProduct = new CartProduct();
    cartProduct.setProductId(rs.getInt("product_id"));
    cartProduct.setImgUrl(rs.getString("image_url"));
    cartProduct.setProductName(rs.getString("product_name"));
    cartProduct.setPrice(rs.getBigDecimal("price"));
    cartProduct.setQuantity(rs.getInt("quantity"));
    return cartProduct;
  }
}

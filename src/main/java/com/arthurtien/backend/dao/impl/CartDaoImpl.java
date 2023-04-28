package com.arthurtien.backend.dao.impl;

import com.arthurtien.backend.dao.CartDao;
import com.arthurtien.backend.dto.AddItemToCartRequest;
import com.arthurtien.backend.model.Cart;
import com.arthurtien.backend.model.CartProduct;
import com.arthurtien.backend.rowmapper.CartProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CartDaoImpl implements CartDao {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public void deleteCartByUserId(Integer userId) {
    String sql = "DELETE FROM cart WHERE user_id = :userId";

    Map<String, Object> map = new HashMap<>();
    map.put("userId", userId);

    namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public void deleteCartProduct(Integer userId, Integer productId) {
    String sql = "DELETE FROM cart" +
        " WHERE user_id = :userId AND product_id = :productId";
    Map<String, Object> map = new HashMap<>();
    map.put("userId", userId);
    map.put("productId", productId);

    namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public Boolean isExistCartbyUserIdAndProductId(Integer userId, Integer productId) {
    String sql = "SELECT count(*)" +
        " FROM cart" +
        " WHERE user_id = :userId AND product_id = :productId";

    Map<String, Object> map = new HashMap<>();
    map.put("userId", userId);
    map.put("productId", productId);

    Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

    if (total > 0) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void updateCartProductQuantity(Integer userId, AddItemToCartRequest addItemToCartRequest) {
    String sql = "UPDATE cart SET quantity = :quantity" +
        " WHERE user_id = :userId AND product_id = :productId";
    Map<String, Object> map = new HashMap<>();
    map.put("quantity", addItemToCartRequest.getQuantity());
    map.put("userId", userId);
    map.put("productId", addItemToCartRequest.getProductId());

    namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public List<CartProduct> getCart(Integer userId) {
    String sql = "SELECT product.product_id, product.image_url, product.product_name," +
        " product.price, cart.quantity" +
        " FROM cart" +
        " JOIN product ON cart.product_id = product.product_id" +
        " WHERE cart.user_id = :userId;";

    Map<String, Object> map = new HashMap<>();
    map.put("userId",userId);

    List<CartProduct> cartProductList = namedParameterJdbcTemplate.query(sql, map, new CartProductRowMapper());

    return cartProductList;
  }

  @Override
  public void addItemToCart(Integer userId, AddItemToCartRequest addItemToCartRequest) {
    String sql = "INSERT INTO cart (user_id, product_id, quantity, created_date, updated_date)" +
        " VALUES (:userId, :productId, :quantity, :createdDate, :updatedDate)";

    Map<String, Object> map = new HashMap<>();
    map.put("userId", userId);
    map.put("productId", addItemToCartRequest.getProductId());
    map.put("quantity", addItemToCartRequest.getQuantity());
    map.put("createdDate", new Date());
    map.put("updatedDate",new Date());

    namedParameterJdbcTemplate.update(sql, map);
  }
}

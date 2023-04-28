package com.arthurtien.backend.rowmapper;

import com.arthurtien.backend.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderItemRowMapper implements RowMapper {
  @Override
  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    OrderItem orderItem = new OrderItem();
    orderItem.setOrderItemId(rs.getInt("order_item_id"));
    orderItem.setOrderId(rs.getInt("order_id"));
    orderItem.setQuantity(rs.getInt("quantity"));
    orderItem.setProductId(rs.getInt("product_id"));
    orderItem.setAmount(rs.getBigDecimal("amount"));

    orderItem.setProductName(rs.getString("product_name"));
    orderItem.setImageUrl(rs.getString("image_url"));
    return orderItem;
  }
}

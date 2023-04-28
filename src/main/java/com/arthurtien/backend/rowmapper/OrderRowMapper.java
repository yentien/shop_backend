package com.arthurtien.backend.rowmapper;

import com.arthurtien.backend.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {
  @Override
  public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
    Order order = new Order();
    order.setOrderId(rs.getInt("order_id"));
    order.setUserId(rs.getInt("user_id"));
    order.setTotalAmount(rs.getBigDecimal("total_amount"));
    order.setSenderName(rs.getString("sender_name"));
    order.setSenderEmail(rs.getString("sender_email"));
    order.setSenderPhone(rs.getString("sender_phone"));
    order.setRecipientName(rs.getString("recipient_name"));
    order.setRecipientPhone(rs.getString("recipient_phone"));
    order.setRecipientAddress(rs.getString("recipient_address"));
    order.setRemark(rs.getString("remark"));
    order.setShipMethod(rs.getString("ship_method"));
    order.setShipFee(rs.getBigDecimal("ship_fee"));
    order.setStatus(rs.getString("status"));
    order.setCreatedDate(rs.getTimestamp("created_date"));
    order.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
    return order;
  }
}

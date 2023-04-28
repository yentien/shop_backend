package com.arthurtien.backend.dao.impl;

import com.arthurtien.backend.dao.OrderDao;
import com.arthurtien.backend.dto.CreateOrderRequest;
import com.arthurtien.backend.model.CartProduct;
import com.arthurtien.backend.model.Order;
import com.arthurtien.backend.model.OrderItem;
import com.arthurtien.backend.rowmapper.OrderItemRowMapper;
import com.arthurtien.backend.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<OrderItem> getOrderItemByOrderId(Integer orderId) {
    String sql = "SELECT o.order_item_id, o.order_id, o.product_id," +
        " o.quantity, o.amount, p.product_name, p.image_url" +
        " FROM order_item o" +
        " LEFT JOIN product p on o.product_id = p.product_id" +
        " WHERE order_id = :orderId";

    Map<String, Object> map = new HashMap<>();
    map.put("orderId", orderId);

    List<OrderItem> orderItemList =
        namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

    if (orderItemList.get(0) == null) {
      return null;
    } else {
      return orderItemList;
    }
  }

  @Override
  public List<Order> getOrderByUserId(Integer userId) {
    String sql = "SELECT order_id, user_id, total_amount," +
        " created_date, last_modified_date, sender_name," +
        " sender_phone, sender_email, recipient_name," +
        " recipient_phone, recipient_address, remark," +
        " ship_method, ship_fee, status" +
        " FROM `order` WHERE user_id = :userId ORDER BY created_date DESC ";

    Map<String, Object> map = new HashMap<>();
    map.put("userId", userId);

    List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

    if (orderList.get(0) == null) {
      return null;
    } else {
      return orderList;
    }

  }

  @Override
  public void createOrderItem(Integer orderId, OrderItem orderItem) {
    String sql = "INSERT INTO order_item (order_id, product_id, quantity, amount)" +
        " VALUES (:orderId, :productId, :quantity, :amount);";

    Map<String, Object> map = new HashMap<>();
    map.put("orderId", orderId);
    map.put("productId", orderItem.getProductId());
    map.put("quantity", orderItem.getQuantity());
    map.put("amount", orderItem.getAmount());

    namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
    String sql = "INSERT INTO `order` (user_id, total_amount, created_date," +
        " last_modified_date, sender_name, sender_phone, sender_email, recipient_name," +
        " recipient_phone, recipient_address, remark, ship_method, ship_fee, status)" +
        " VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate, :senderName," +
        " :senderPhone, :senderEmail, :recipientName, :recipientPhone, :recipientAddress," +
        " :remark, :shipMethod, :shipFee, :status) ";

    Map<String, Object> map = new HashMap<>();
    map.put("userId", userId);
    map.put("totalAmount", createOrderRequest.getTotalAmount());

    Date date = new Date();
    map.put("createdDate", date);
    map.put("lastModifiedDate", date);
    map.put("senderName", createOrderRequest.getSenderName());
    map.put("senderPhone", createOrderRequest.getSenderPhone());
    map.put("senderEmail", createOrderRequest.getSenderEmail());
    map.put("recipientName", createOrderRequest.getRecipientName());
    map.put("recipientPhone", createOrderRequest.getRecipientPhone());
    map.put("recipientAddress", createOrderRequest.getRecipientAddress());
    map.put("remark", createOrderRequest.getRemark());
    map.put("shipMethod", createOrderRequest.getShipMethod());
    map.put("shipFee", createOrderRequest.getShipFee());
    map.put("status", "完成");

    KeyHolder keyHolder = new GeneratedKeyHolder();

    namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

    Integer orderId = keyHolder.getKey().intValue();

    return orderId;
  }
}

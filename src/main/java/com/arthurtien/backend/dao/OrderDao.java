package com.arthurtien.backend.dao;

import com.arthurtien.backend.dto.CreateOrderRequest;
import com.arthurtien.backend.model.CartProduct;
import com.arthurtien.backend.model.Order;
import com.arthurtien.backend.model.OrderItem;

import java.util.List;

public interface OrderDao {
  Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
  void createOrderItem(Integer orderId, OrderItem orderItem);
  List<Order> getOrderByUserId(Integer userId);
  List<OrderItem> getOrderItemByOrderId(Integer orderId);
}

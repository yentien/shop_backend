package com.arthurtien.backend.service;

import com.arthurtien.backend.dto.CreateOrderRequest;
import com.arthurtien.backend.model.CartProduct;
import com.arthurtien.backend.model.Order;

import java.util.List;

public interface OrderService {
  Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
  void createOrderItem(Integer orderId, List<CartProduct> cartProductList);
  List<Order> getOrderByUserId(Integer userId);
}

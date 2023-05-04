package com.arthurtien.backend.service.impl;

import com.arthurtien.backend.dao.OrderDao;
import com.arthurtien.backend.dto.CreateOrderRequest;
import com.arthurtien.backend.model.CartProduct;
import com.arthurtien.backend.model.Order;
import com.arthurtien.backend.model.OrderItem;
import com.arthurtien.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderDao orderDao;

  @Override
  public void deleteOrderItemById(Integer orderId) {
    orderDao.deleteOrderItemById(orderId);
  }

  @Override
  public void deleteOrderById(Integer orderId) {
    orderDao.deleteOrderById(orderId);
  }

  @Override
  public List<Order> getOrders() {
    List<Order> orderList = orderDao.getOrders();
    for (Order order:orderList) {
      List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(order.getOrderId());
      order.setOrderItemList(orderItemList);
    }
    return orderList;
  }

  @Override
  public List<Order> getOrderByUserId(Integer userId) {
    List<Order> orderList = orderDao.getOrderByUserId(userId);

    if (orderList == null) {
      return null;
    }else {
      for (Order order:orderList) {
        List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(order.getOrderId());
        order.setOrderItemList(orderItemList);
      }
    }

    return orderList;
  }

  @Override
  public void createOrderItem(Integer orderId, List<CartProduct> cartProductList) {
    for (CartProduct cartProduct : cartProductList) {
      OrderItem orderItem = new OrderItem();
      orderItem.setOrderId(orderId);
      orderItem.setProductId(cartProduct.getProductId());
      orderItem.setQuantity(cartProduct.getQuantity());
      orderItem.setAmount(cartProduct.getPrice());

      orderDao.createOrderItem(orderId, orderItem);
    }
  }

  @Override
  public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
    return orderDao.createOrder(userId, createOrderRequest);
  }
}

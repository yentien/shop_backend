package com.arthurtien.backend.controller;

import com.arthurtien.backend.dto.CreateOrderRequest;
import com.arthurtien.backend.model.CartProduct;
import com.arthurtien.backend.model.Order;
import com.arthurtien.backend.service.CartService;
import com.arthurtien.backend.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private CartService cartService;

  // 刪除訂單
  @DeleteMapping("/admin/orders/{orderId}")
  public ResponseEntity<?> deleteOrderById(@PathVariable Integer orderId) {
    orderService.deleteOrderById(orderId);
    orderService.deleteOrderItemById(orderId);
    return ResponseEntity.status(200).body("訂單刪除成功");
  }

  // 查詢訂單(所有)
  @GetMapping("/admin/orders")
  public ResponseEntity<?> getOrders() {
    List<Order> orderList = orderService.getOrders();
    return ResponseEntity.status(200).body(orderList);
  }

  // 查詢訂單
  @GetMapping("/users/{userId}/order")
  public ResponseEntity<?> getOrderByUserId(@PathVariable Integer userId) {
    List<Order> orderList = orderService.getOrderByUserId(userId);

    if (orderList != null && orderList.size() != 0) {
      return ResponseEntity.status(200).body(orderList);
    }else {
      return ResponseEntity.status(400).body("Empty orderList");
    }
  }

  // 建立訂單
  @PostMapping("/users/{userId}/order")
  public ResponseEntity<?> createOrder(
      @PathVariable Integer userId,
      @RequestBody @Valid CreateOrderRequest createOrderRequest
  ) {

    // 查詢該user購物車
    List<CartProduct> cartProductList = cartService.getCart(userId);

    // 計算總金額
    BigDecimal totalAmount = BigDecimal.ZERO;
    for (CartProduct cartProduct: cartProductList) {
      BigDecimal quanitity = BigDecimal.valueOf(cartProduct.getQuantity());
      totalAmount = totalAmount.add( quanitity.multiply(cartProduct.getPrice()));
    }
    totalAmount = totalAmount.add(createOrderRequest.getShipFee());
    createOrderRequest.setTotalAmount(totalAmount);

    // 建立order
    Integer orderId = orderService.createOrder(userId, createOrderRequest);

    // 建立orderItem
    orderService.createOrderItem(orderId, cartProductList);

    // 刪除購物車商品
    cartService.deleteCartByUserId(userId);

    return ResponseEntity.status(201).body("order created");
  }
}

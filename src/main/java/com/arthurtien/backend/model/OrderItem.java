package com.arthurtien.backend.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
  private Integer orderItemId;
  private Integer orderId;
  private Integer productId;
  private Integer quantity;
  private BigDecimal amount;

  private String productName;
  private String imageUrl;
}

package com.arthurtien.backend.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProduct {
  private Integer productId;
  private String imgUrl;
  private String productName;
  private BigDecimal price;
  private Integer quantity;
}

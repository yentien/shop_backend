package com.arthurtien.backend.dto;

import lombok.Data;

@Data
public class AddItemToCartRequest {
  private Integer productId;
  private Integer quantity;
}

package com.arthurtien.backend.model;

import lombok.Data;

@Data
public class Cart {
  private Integer id;
  private Integer userId;
  private Integer productId;
  private Integer queantity;
  private Integer createdDate;
  private Integer updatedDate;
}

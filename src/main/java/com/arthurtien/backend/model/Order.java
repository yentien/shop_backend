package com.arthurtien.backend.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Order {
  private Integer orderId;
  private Integer userId;
  private BigDecimal totalAmount;
  private Date createdDate;
  private Date lastModifiedDate;
  private String senderName;
  private String senderPhone;
  private String senderEmail;
  private String recipientName;
  private String recipientPhone;
  private String recipientAddress;
  private String remark;
  private String shipMethod;
  private BigDecimal shipFee;
  private String status;

  private List<OrderItem> OrderItemList;
}

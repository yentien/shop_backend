package com.arthurtien.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderRequest {

  @NotBlank
  private String senderName;
  @NotBlank
  private String senderPhone;
  @NotBlank
  private String senderEmail;
  @NotBlank
  private String recipientName;
  @NotBlank
  private String recipientPhone;
  @NotBlank
  private String recipientAddress;

  private BigDecimal totalAmount;
  private String remark;
  @NotBlank
  private String shipMethod;
  @NotNull
  private BigDecimal shipFee;
}

package com.arthurtien.backend.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServerResponse<T> implements Serializable {
  private Integer code; // 0:未登入, 1:登入成功, 2:登入失敗, 3.權限不足
  private String authorization; // 認證
  private Long total; // 查詢出來的總紀錄
  private T data; // 查詢出來的數據
  private String message; // 訊息

  public ServerResponse() {

  }
}

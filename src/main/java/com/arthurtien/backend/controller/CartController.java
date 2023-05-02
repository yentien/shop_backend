package com.arthurtien.backend.controller;

import com.arthurtien.backend.dto.AddItemToCartRequest;
import com.arthurtien.backend.model.Cart;
import com.arthurtien.backend.model.CartProduct;
import com.arthurtien.backend.model.SysUser;
import com.arthurtien.backend.service.CartService;
import com.arthurtien.backend.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class CartController {
  private Logger log = Logger.getLogger(UserController.class.getName());

  @Autowired
  private CartService cartService;
//  @Autowired
//  private SysUserService sysUserService;

  @DeleteMapping("/users/{userId}/cart")
  public ResponseEntity<?> deleteCartProduct(
      @PathVariable Integer userId,
      @RequestBody Cart cart
      ) {
    // 檢查userId是否存在
//    SysUser sysUser = sysUserService.getUserById(userId);
//    if (sysUser == null) {
//      return ResponseEntity.status(400).body("user not found");
//    }
//
//    log.info(cart.toString());

    // 檢查商品是否已存在購物車
    Boolean isExistCartProduct = cartService.isExistCartbyUserIdAndProductId(userId, cart.getProductId());

    if (isExistCartProduct) {
      // 刪除該購物車商品
      cartService.deleteCartProduct(userId, cart.getProductId());
    } else {
      return ResponseEntity.status(400).body("product doesn't exist in cart");
    }

    // 查詢該用戶購物車
    List<CartProduct> cartProductList = cartService.getCart(userId);
    return ResponseEntity.status(200).body(cartProductList);
  }

  @PatchMapping("/users/{userId}/cart")
  public ResponseEntity<?> updateCartProductQuantity(
      @PathVariable Integer userId,
      @RequestBody AddItemToCartRequest addItemToCartRequest
  ) {
    // 檢查userId是否存在
//    SysUser sysUser = sysUserService.getUserById(userId);
//    if (sysUser == null) {
//      return ResponseEntity.status(400).body("user not found");
//    }

    // 檢查商品是否已存在購物車
    Boolean isExistCartProduct = cartService.isExistCartbyUserIdAndProductId(userId,addItemToCartRequest.getProductId());
    if (isExistCartProduct) {
      // 修改購物車商品資訊
      cartService.updateCartProductQuantity(userId, addItemToCartRequest);
    } else {
      return ResponseEntity.status(400).body("product doesn't exist in cart");
    }

    // 查詢該用戶購物車
    List<CartProduct> cartProductList = cartService.getCart(userId);
    return ResponseEntity.status(200).body(cartProductList);
  }

  // 新增商品至購物車
  @PostMapping("/users/{userId}/cart")
  public ResponseEntity<?> addItemToCart(
      @PathVariable Integer userId,
      @RequestBody AddItemToCartRequest addItemToCartRequest
      ) {

    // 檢查userId是否存在
//    SysUser sysUser = sysUserService.getUserById(userId);
//    if (sysUser == null) {
//      return ResponseEntity.status(400).body("user not found");
//    }

    // 檢查商品是否已存在購物車
    Boolean isExistCartProduct = cartService.isExistCartbyUserIdAndProductId(userId, addItemToCartRequest.getProductId());
    if (isExistCartProduct) {
      // 修改購物車商品資訊
      cartService.updateCartProductQuantity(userId, addItemToCartRequest);
    } else {
      // 新增商品至購物車
      cartService.addItemToCart(userId, addItemToCartRequest);
    }

    // 查詢該用戶購物車
    List<CartProduct> cartProductList = cartService.getCart(userId);
    return ResponseEntity.status(201).body(cartProductList);
  }

  // 查詢購物車
  @GetMapping("/users/{userId}/cart")
  public ResponseEntity<?> getCart(@PathVariable Integer userId) {

    // 檢查userId是否存在
//    SysUser sysUser = sysUserService.getUserById(userId);
//    if (sysUser == null) {
//      return ResponseEntity.status(400).body("user not found");
//    }

    // 查詢購物車
    List<CartProduct> cartProductList = cartService.getCart(userId);

    return ResponseEntity.status(200).body(cartProductList);
  }
}

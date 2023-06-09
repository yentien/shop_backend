package com.arthurtien.backend.service;

import com.arthurtien.backend.dto.AddItemToCartRequest;
import com.arthurtien.backend.model.Cart;
import com.arthurtien.backend.model.CartProduct;

import java.util.List;

public interface CartService {
  void addItemToCart(Integer userId, AddItemToCartRequest addItemToCartRequest);

  List<CartProduct> getCart(Integer userId);

  Boolean isExistCartbyUserIdAndProductId(Integer userId,Integer productId);

  void updateCartProductQuantity (Integer userId,AddItemToCartRequest addItemToCartRequest);

  void deleteCartProduct(Integer userId, Integer productId);

  void deleteCartByUserId(Integer userId);
}

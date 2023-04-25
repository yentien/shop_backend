package com.arthurtien.backend.dao;

import com.arthurtien.backend.dto.AddItemToCartRequest;
import com.arthurtien.backend.model.Cart;
import com.arthurtien.backend.model.CartProduct;

import java.util.List;

public interface CartDao {
  void addItemToCart(Integer userId, AddItemToCartRequest addItemToCartRequest);

  List<CartProduct> getCart(Integer userId);

  Boolean isExistCartbyUserIdAndProductId(Integer userId, Integer productId);

  void updateCartProductQuantity (Integer userId,AddItemToCartRequest addItemToCartRequest);

  void deleteCartProduct(Integer userId, Integer productId);
}

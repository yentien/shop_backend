package com.arthurtien.backend.service.impl;

import com.arthurtien.backend.dao.CartDao;
import com.arthurtien.backend.dto.AddItemToCartRequest;
import com.arthurtien.backend.model.Cart;
import com.arthurtien.backend.model.CartProduct;
import com.arthurtien.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartServiceImpl implements CartService {

  @Autowired
  private CartDao cartDao;

  @Override
  public void deleteCartProduct(Integer userId, Integer productId) {
    cartDao.deleteCartProduct(userId, productId);
  }

  // 查詢購物車是否有該商品
  @Override
  public Boolean isExistCartbyUserIdAndProductId(Integer userId, Integer productId) {
    return cartDao.isExistCartbyUserIdAndProductId(userId, productId);
  }

  // 更新購物車裡該已存在該商品的數量
  @Override
  public void updateCartProductQuantity(Integer userId, AddItemToCartRequest addItemToCartRequest) {
    cartDao.updateCartProductQuantity(userId, addItemToCartRequest);
  }

  // 查詢購物車
  @Override
  public List<CartProduct> getCart(Integer userId) {
    return cartDao.getCart(userId);
  }

  // 商品加入購物車
  @Override
  public void addItemToCart(Integer userId, AddItemToCartRequest addItemToCartRequest) {
    cartDao.addItemToCart(userId, addItemToCartRequest);
  }
}

package com.arthurtien.backend.controller;

import com.arthurtien.backend.model.Product;
import com.arthurtien.backend.service.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    // delete product
    @DeleteMapping("/admin/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.OK).body("刪除成功");
    }

    // modify product
    @PostMapping("/admin/product/{productId}")
    public ResponseEntity<?> modifyProduct(
        @PathVariable Integer productId,
        @RequestBody Product product) {
        productService.modifyProduct(productId,product);
        return ResponseEntity.status(HttpStatus.OK).body("修改成功");
    }

    // create product
    @PostMapping("/admin/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Integer productId = productService.createProduct(product);
        Product createdProduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(createdProduct);
    }

    // read 全部商品
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> productList = productService.getProducts();

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    // read 單一商品
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}

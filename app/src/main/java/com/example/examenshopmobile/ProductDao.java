package com.example.examenshopmobile;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);

    @Query("SELECT * FROM Product")
    List<Product> getAllProducts();

    @Query("SELECT * FROM Product WHERE id = :productId LIMIT 1")
    Product getProductById(int productId);

    @Query("SELECT * FROM Product WHERE id IN (SELECT productId FROM OrderItem WHERE orderId = :orderId)")
    List<Product> getProductsForOrder(int orderId);

    @Query("SELECT * FROM Product ORDER BY price ASC")
    List<Product> getAllProductsSortedByPriceAsc();
    @Query("SELECT * FROM Product ORDER BY price DESC")
    List<Product> getAllProductsSortedByPriceDesc();

}



package com.example.examenshopmobile;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(Cart cart);

    @Query("SELECT * FROM Cart")
    List<Cart> getAllCartItems();

    @Query("DELETE FROM Cart WHERE productId = :productId")
    void deleteByProductId(int productId);

    @Query("UPDATE Cart SET quantity = :quantity WHERE productId = :productId")
    void updateQuantity(int productId, int quantity);
}

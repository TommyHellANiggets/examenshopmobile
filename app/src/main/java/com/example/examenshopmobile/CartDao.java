package com.example.examenshopmobile;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(Cart cart);

    @Query("SELECT * FROM Cart")
    List<Cart> getAllCartItems();

    @Query("DELETE FROM Cart WHERE productId = :productId")
    void removeFromCart(int productId);
}

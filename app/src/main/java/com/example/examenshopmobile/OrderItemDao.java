package com.example.examenshopmobile;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderItemDao {
    @Insert
    void insertOrderItems(List<OrderItem> orderItems);

    @Query("SELECT * FROM OrderItem WHERE orderId = :orderId")
    List<OrderItem> getOrderItemsByOrderId(int orderId);


}

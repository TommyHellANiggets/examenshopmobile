package com.example.examenshopmobile;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    long insertOrder(Order order);

    @Query("SELECT * FROM `Order`")
    List<Order> getAllOrders();

    @Insert
    void insertOrderItems(List<OrderItem> orderItems);

    @Query("SELECT * FROM `Order` WHERE orderId = :orderId")
    Order getOrderById(int orderId);

    


}

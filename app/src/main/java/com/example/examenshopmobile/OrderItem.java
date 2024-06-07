package com.example.examenshopmobile;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OrderItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int orderId;
    public int productId;
    public int quantity;
    public double price;
}

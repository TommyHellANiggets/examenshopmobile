package com.example.examenshopmobile;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    public int orderId;
    public String name;
    public String phone;
    public String email;
    public String address;
    public double totalAmount;
    public String orderDate;
    public String orderNumber;
    @ColumnInfo(name = "payment_method")
    public String paymentMethod;
}

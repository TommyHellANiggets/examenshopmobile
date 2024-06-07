package com.example.examenshopmobile;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public int quantity;
    public int price;
    public String description;
    public String imagePath;
}

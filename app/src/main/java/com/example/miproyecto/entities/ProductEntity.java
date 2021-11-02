package com.example.miproyecto.entities;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class ProductEntity implements Serializable {
    @DocumentId
    private String id;
    private String name;
    private Double price;
    private int stock;
    private String category;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Double getPrice() {

        return price;
    }

    public void setPrice(Double price) {

        this.price = price;
    }

    public int getStock() {

        return stock;
    }

    public void setStock(int stock) {

        this.stock = stock;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }
}

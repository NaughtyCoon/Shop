package org.example;

import java.util.Date;

public class Sale {

    private int id;         // ID магазина, где произошла продажа
    private int storeId;    // ID проданного товара
    private int productId;  // ID проданного товара
    private int quantity;   // количество проданного товара
    private String date;      // Дата продажи

    public Sale(int id, int storeId, int productId, int quantity, String date) {

        this.id = id;
        this.storeId = storeId;
        this.productId = productId;
        this.quantity = quantity;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

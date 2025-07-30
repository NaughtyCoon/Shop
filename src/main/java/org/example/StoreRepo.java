package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreRepo {

    private List<Store> stores = new ArrayList<>(Arrays.asList(
            new Store(1, "Пятерочка", "ул. Бассейная, 10", true),
            new Store(2, "Магнит", "ул. Ленина, 5", true),
            new Store(3, "Перекресток", "ул. Бассейная, 15", false),
            new Store(4, "Дикси", "ул. Гагарина, 20", true)
    ));

    private List<Product> products = new ArrayList<>(Arrays.asList(
            new Product(1, "Помидоры", "Овощи", 120.0),
            new Product(2, "Огурцы", "Овощи", 90.0),
            new Product(3, "Яблоки", "Фрукты", 80.0),
            new Product(4, "Молоко", "Молочные продукты", 70.0)
    ));

    private List<Warehouse> warehouses = new ArrayList<>(Arrays.asList(
            new Warehouse(1, 1, 50),
            new Warehouse(1, 2, 30),
            new Warehouse(2, 1, 20),
            new Warehouse(3, 3, 100),
            new Warehouse(4, 4, 40)
    ));

    private List<Sale> sales = new ArrayList<>(Arrays.asList(
            new Sale(1, 1, 1, 5, "2024-05-01"),  // В Пятерочке продано 5 помидоров
            new Sale(2, 1, 2, 3, "2024-05-01"),  // 3 огурца
            new Sale(3, 2, 1, 2, "2024-05-02"),  // В Магните 2 помидора
            new Sale(4, 3, 3, 10, "2024-05-03")  // В Перекрестке 10 яблок
    ));

    public List<Store> getStores() {
        return stores;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}

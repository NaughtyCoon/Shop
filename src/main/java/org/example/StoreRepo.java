package org.example;

import java.util.Arrays;
import java.util.List;

public class StoreRepo {

    private final List<Store> stores = Arrays.asList(
            new Store(1, "Пятерочка", "ул. Бассейная, 10", true),
            new Store(2, "Магнит", "ул. Ленина, 5", true),
            new Store(3, "Перекресток", "ул. Бассейная, 15", false),
            new Store(4, "Дикси", "ул. Гагарина, 20", true)
    );

    private final List<Product> products = Arrays.asList(
            new Product(1, "Помидоры", "Овощи", 120.0),
            new Product(2, "Огурцы", "Овощи", 90.0),
            new Product(3, "Яблоки", "Фрукты", 80.0),
            new Product(4, "Молоко", "Молочные продукты", 70.0)
    );

    private final List<Warehouse> warehouses = Arrays.asList(
            new Warehouse(1, 1, 50),  // В магазине 1 (Пятерочка) 50 помидоров
            new Warehouse(1, 2, 30),   // Огурцов 30
            new Warehouse(2, 1, 20),   // В Магните 20 помидоров
            new Warehouse(3, 3, 100),  // В Перекрестке 100 яблок
            new Warehouse(4, 4, 40)    // В Дикси 40 молока
    );

    private final List<Sale> sales = Arrays.asList(
            new Sale(1, 1, 1, 5, "2024-05-01"),  // В Пятерочке продано 5 помидоров
            new Sale(2, 1, 2, 3, "2024-05-01"),  // 3 огурца
            new Sale(3, 2, 1, 2, "2024-05-02"),  // В Магните 2 помидора
            new Sale(4, 3, 3, 10, "2024-05-03")  // В Перекрестке 10 яблок
    );

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

}

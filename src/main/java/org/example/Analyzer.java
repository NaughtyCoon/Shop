package org.example;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map.Entry;

public class Analyzer {

    private StoreRepo storeRepo = new StoreRepo();

    public StoreRepo getStoreRepo() {
        return storeRepo;
    }

    public void setStoreRepo(StoreRepo storeRepo) {
        this.storeRepo = storeRepo;
    }

    public List<Store> findOpenStoreOnBasseina() {
        return
                storeRepo.getStores().stream()
                        .filter(store -> store.isOpen() && (store.getAddress().contains("Бассейная")))
                        .toList();
    }

    public int getGlobalTomatoQuantity() {

        return
                storeRepo.getWarehouses().stream()
                        .filter(warehouse -> warehouse.getProductId() == 1)
                        .mapToInt(Warehouse::getQuantity)
                        .sum();

    }

    public Map<String, Integer> getProductRemnants() {

        Map<Integer, Integer> summedQuantities =
                storeRepo.getWarehouses().stream()
                        .collect(Collectors.toMap(
                                Warehouse::getProductId,
                                Warehouse::getQuantity,
                                Integer::sum
                        ));

        Map<Integer, String> storeNames =
                storeRepo.getProducts().stream()
                        .collect(Collectors.toMap(
                                Product::getId,
                                Product::getName
                        ));

        return
                summedQuantities.entrySet().stream()
                        .filter(entry -> storeNames.containsKey(entry.getKey()) && entry.getValue() < 50)
                        .collect(Collectors.toMap(
                                entry -> storeNames.get(entry.getKey()),
                                Map.Entry::getValue
                        ));

    }


    public Map<String, Integer> getThreeBestSellers() {

        Map<Integer, Integer> topThreeProducts = storeRepo.getSales().stream()
                .collect(Collectors.groupingBy(
                        Sale::getProductId,
                        Collectors.summingInt(Sale::getQuantity)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        Entry::getKey,
                        Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        Map<Integer, String> productNames =
                storeRepo.getProducts().stream()
                        .collect(Collectors.toMap(
                                Product::getId,
                                product -> Optional.ofNullable(product.getName()).orElse("Неизвестный товар")
                        ));

        return
                topThreeProducts.entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> productNames.get(entry.getKey()),
                                Map.Entry::getValue,
                                (a, b) -> a,
                                LinkedHashMap::new
                        ));

    }

    public List<Store> getStoresWithoutFruits() {

        List<Product> notFruitCategoryProducts = storeRepo.getProducts().stream()
                .filter(product -> !Objects.equals(product.getCategory(), "Фрукты"))
                .toList();

        List<Warehouse> noFruitWarehouses = storeRepo.getWarehouses().stream()
                .filter(warehouse -> notFruitCategoryProducts.stream()
                        .anyMatch(product -> product.getId() == warehouse.getProductId()))
                .toList();

        return
                storeRepo.getStores().stream()
                        .filter(store -> noFruitWarehouses.stream()
                                .anyMatch(warehouse -> warehouse.getStoreId() == store.getId()))
                        .toList();

    }

    public Map<Store, Double> getMayShopsIncome() {

        return
                storeRepo.getStores().stream()
                        .collect(Collectors.toMap(
                                store -> store,
                                store -> storeRepo.getSales().stream()
                                        .filter(sale -> store.getId() == sale.getStoreId() && sale.getDate().getMonthValue() == 5)
                                        .mapToDouble(sale -> sale.getQuantity() * storeRepo.getProducts().stream()
                                                .filter(product -> product.getId() == sale.getProductId())
                                                .findFirst()
                                                .map(Product::getPrice)
                                                .orElse(0.0)
                                        )
                                        .sum()
                        ));

    }

    public List<Product> getNeverSoldProducts() {

        return
                getStoreRepo().getWarehouses().stream()
                        .map(Warehouse::getProductId)
                        .filter(productId -> getStoreRepo().getSales().stream()
                                .noneMatch(sale -> sale.getProductId() == productId))
                        .distinct()
                        .sorted()
                        .toList().stream()
                        .map(prodId -> getStoreRepo().getProducts().stream()
                                .filter(e -> e.getId() == prodId)
                                .findFirst()
                                .orElse(new Product(prodId, "Неизвестный продукт с id = " + prodId, "Неизвестно", 0.0)))
                        .toList();

    }

    public Map<String, Double> getAvgPriceByCategories() {

        return
                getStoreRepo().getProducts().stream()
                        .collect(Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.collectingAndThen(
                                        Collectors.averagingDouble(Product::getPrice),
                                        avg -> Math.round(avg * 100) / 100.0
                                )));

    }



}

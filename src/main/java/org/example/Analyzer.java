package org.example;

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

    public Map<Store, Double> getMayShopsRevenue() {

        Map<Integer, Integer> maySalesRevenues = storeRepo.getSales().stream()
                .filter(sale -> sale.getDate().getMonthValue() == 5)
                .collect(Collectors.toMap(
                        Sale::getStoreId,
                        sale -> storeRepo.getProducts().stream()
                                .findFirst(product -> product.getId() == sale.getProductId())
                                .toList()

                        ,
                        Integer::sum
                ));



    }

}

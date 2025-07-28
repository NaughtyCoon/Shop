package org.example;

import java.util.ArrayList;
import java.util.List;

public class Analyzer {

    private List<Store> stores = new ArrayList<>();
    private StoreRepo storeRepo = new StoreRepo();


    public Analyzer(List<Store> stores) {
        this.stores = stores;
    }

    public List<Store> findOpenStoreOnBasseina() {
        return stores.stream()
                .filter(store -> store.isOpen() && (store.getAddress().contains("Бассейная")))
                .toList();
    }

    public int globalTomatoQuantity() {
        return storeRepo.getWarehouses().stream()
                .filter(warehouse -> warehouse.getProductId() == 1)
                .mapToInt()
                .sum();
    }

}

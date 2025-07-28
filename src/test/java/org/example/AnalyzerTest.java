package org.example;

import org.junit.jupiter.api.Test;
import org.example.Analyzer;
import org.example.Store;
import org.example.StoreRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalyzerTest {

    private Analyzer analyzer;
    private StoreRepo storeRepo;



    @Test
    void findOpenStoreOnBasseina_whenEmptyList_thenReturnEmptyList() {

        List<Store> stores = new ArrayList<>();
        analyzer = new Analyzer(stores);

        List<Store> openStores = analyzer.findOpenStoreOnBasseina();

        assertTrue(openStores.isEmpty());

    }

    @Test
    void findOpenStoreOnBasseina_whenNoMatchingStores_thenReturnEmptyList() {

        List<Store> stores = List.of(
                new Store(3, "Перекресток", "ул. Димитрова, 15", false),
                new Store(4, "Дикси", "ул. Гагарина, 20", true));
        analyzer = new Analyzer(stores);

        List<Store> openStores = analyzer.findOpenStoreOnBasseina();

        assertTrue(openStores.isEmpty());

    }

    @Test
    void findOpenStoreOnBasseina_whenOneOpenStore_thenReturnThisStore() {

        List<Store> stores = List.of(
                new Store(1, "Пятерочка", "ул. Бассейная, 10", true),
                new Store(2, "Магнит", "ул. Ленина, 5", true)
        );
        analyzer = new Analyzer(stores);

        List<Store> openStores = analyzer.findOpenStoreOnBasseina();

        assertEquals(1, openStores.size());
        assertEquals(stores.get(0).getId(), openStores.get(0).getId());

    }

    @Test
    void findOpenStoreOnBasseina_whenMultipleOpenStores_thenReturnAll() {

        Store firstStore = new Store(1, "Пятерочка", "ул. Бассейная, 10", true);
        Store secondStore = new Store(2, "Магнит", "ул. Бассейная, 5", true);
        List<Store> stores = List.of(
                firstStore,
                secondStore,
                new Store(2, "Магнит", "ул. Ленина, 5", true)
        );
        analyzer = new Analyzer(stores);

        List<Store> openStores = analyzer.findOpenStoreOnBasseina();

        assertEquals(2, openStores.size());
        assertTrue(openStores.containsAll(List.of(firstStore, secondStore)));

    }

    @Test   // Задача 1
    void findOpenStoreOnBasseina_whenOneStoreClosedAndOneStoreOpenAndBothOnBasseina_thenExcludedClosed() {

        Store firstStore = new Store(1, "Пятерочка", "ул. Бассейная, 10", false);
        Store secondStore = new Store(2, "Магнит", "ул. Бассейная, 5", true);
        List<Store> stores = List.of(
                firstStore,
                secondStore
        );
        analyzer = new Analyzer(stores);

        List<Store> openStores = analyzer.findOpenStoreOnBasseina();

        assertEquals(1, openStores.size());
        assertEquals(secondStore.getName(), openStores.get(0).getName());

    }

    @Test   // Задача 2
    void globalTomatoQuantity_whenShopListIsEmpty_thenReturnZero() {

        List<Store> stores = new ArrayList<>();
        analyzer = new Analyzer(stores);

        assertEquals(0, analyzer.globalTomatoQuantity());

    }
}

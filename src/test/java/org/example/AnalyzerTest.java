package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalyzerTest {

//    private final StoreRepo storeRepo = new StoreRepo();
//    private Analyzer analyzer = new Analyzer(storeRepo);



    @Test
    void findOpenStoreOnBasseina_whenEmptyList_thenReturnEmptyList() {

        Analyzer analyzer = new Analyzer();
        analyzer.getStoreRepo().setStores(List.of());

        List<Store> openStores = analyzer.findOpenStoreOnBasseina();

        assertTrue(openStores.isEmpty());

    }

    @Test
    void findOpenStoreOnBasseina_whenNoMatchingStores_thenReturnEmptyList() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setStores(List.of(
                new Store(3, "Перекресток", "ул. Димитрова, 15", false),
                new Store(4, "Дикси", "ул. Гагарина, 20", true)));


        List<Store> openStores = analyzer.findOpenStoreOnBasseina();

        assertTrue(openStores.isEmpty());

    }

    @Test
    void findOpenStoreOnBasseina_whenOneOpenStore_thenReturnThisStore() {

        Analyzer analyzer = new Analyzer();

        Store firstStore = new Store(1, "Пятерочка", "ул. Бассейная, 10", true);
        Store secondStore = new Store(2, "Магнит", "ул. Ленина, 5", true);

        analyzer.getStoreRepo().setStores(List.of(
                firstStore,
                secondStore
        ));

        List<Store> openStores = analyzer.findOpenStoreOnBasseina();

        assertEquals(1, openStores.size());
        assertEquals(analyzer.getStoreRepo().getStores().get(0).getId(), openStores.get(0).getId());

    }

    @Test
    void findOpenStoreOnBasseina_whenMultipleOpenStores_thenReturnAll() {

        Analyzer analyzer = new Analyzer();

        Store firstStore = new Store(1, "Пятерочка", "ул. Бассейная, 10", true);
        Store secondStore = new Store(2, "Магнит", "ул. Бассейная, 5", true);
        Store thirdStore = new Store(2, "Магнит", "ул. Ленина, 5", true);

        analyzer.getStoreRepo().setStores(List.of(
                firstStore,
                secondStore,
                thirdStore
        ));

        List<Store> openStores = analyzer.findOpenStoreOnBasseina();

        assertEquals(2, openStores.size());
        assertTrue(openStores.containsAll(List.of(firstStore, secondStore)));

    }

    // Задача 1

    @Test
    void findOpenStoreOnBasseina_whenOneStoreClosedAndOneStoreOpenAndBothOnBasseina_thenExcludeClosed() {

        Analyzer analyzer = new Analyzer();

        Store firstStore = new Store(1, "Пятерочка", "ул. Бассейная, 10", false);
        Store secondStore = new Store(2, "Магнит", "ул. Бассейная, 5", true);

        analyzer.getStoreRepo().setStores(List.of(
                firstStore,
                secondStore
        ));

        List<Store> openStores = analyzer.findOpenStoreOnBasseina();

        assertEquals(1, openStores.size());
        assertEquals(secondStore.getName(), openStores.get(0).getName());

    }

    // Задача 2

    @Test
    void getGlobalTomatoQuantity_whenWarehouseListIsEmpty_thenReturnZero() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setWarehouses(List.of());

        assertEquals(0, analyzer.getGlobalTomatoQuantity());

    }

    @Test
    void getGetGlobalTomatoQuantity_whenWarehouseListContainsNoTomatoRecords_thenReturnZero() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setWarehouses(List.of(
                new Warehouse(1, 3, 50),
                new Warehouse(1, 2, 30),
                new Warehouse(2, 4, 20),
                new Warehouse(3, 3, 100),
                new Warehouse(4, 4, 40)
        ));

        assertEquals(0, analyzer.getGlobalTomatoQuantity());

    }

    @Test
    void getGlobalTomatoQuantity_whenWarehouseListContainsTomatoRecords_thenReturnSumOfTomatoesFromAllWarehouses() {

        Analyzer analyzer = new Analyzer();

        assertEquals(70, analyzer.getGlobalTomatoQuantity());

    }

    // Задача 3

    @Test
    void getProductRemnants_whenProductListIsEmpty_thenReturnEmptyMap() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setProducts(List.of());

        assertTrue(analyzer.getProductRemnants().isEmpty());

    }

    @Test
    void getProductRemnants_whenWarehouseListIsEmpty_thenReturnEmptyMap() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setWarehouses(List.of());

        assertTrue(analyzer.getProductRemnants().isEmpty());

    }

    @Test
    void getProductRemnants_whenEachProductIsAbove50_thenReturnEmptyMap() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setWarehouses(List.of(
                new Warehouse(1, 1, 50),
                new Warehouse(1, 2, 60),
                new Warehouse(2, 1, 20),
                new Warehouse(3, 3, 100),
                new Warehouse(4, 4, 70)
        ));

        assertTrue(analyzer.getProductRemnants().isEmpty());

    }

    @Test
    void getProductRemnants_whenProductsBelow50Exist_thenReturnMapForThatProducts() {

        Analyzer analyzer = new Analyzer();

        assertEquals(2, analyzer.getProductRemnants().size());
        assertEquals(30, analyzer.getProductRemnants().get("Огурцы"));
        assertEquals(40, analyzer.getProductRemnants().get("Молоко"));

    }

    // Задача 4

    @Test
    void getThreeBestSellers_whenSalesListIsEmpty_thenReturnEmptyMap() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setSales(List.of());

        assertEquals(0, analyzer.getThreeBestSellers().size());

    }

    @Test
    void getThreeBestSellers_whenAnyProductsNameIsNull_thenReturnUnknownNameForThatProduct() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().getProducts().add(new Product(5, null, "Овощи", 120.0));
        analyzer.getStoreRepo().getSales().add(new Sale(5, 1, 5, 100, "2024-05-01"));

        assertTrue(analyzer.getThreeBestSellers().containsKey("Неизвестный товар"));

    }

    @Test
    void getThreeBestSellers_whenSalesListExists_thenReturnMaxThreeTopSoldProducts() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().getProducts().add(new Product(5, null, "Овощи", 120.0));
        analyzer.getStoreRepo().getSales().add(new Sale(5, 1, 5, 100, "2024-05-01"));
        List<String> keys = new ArrayList<>(analyzer.getThreeBestSellers().keySet());

        assertEquals(3, keys.size());
        assertEquals("Неизвестный товар", keys.get(0));
        assertEquals("Яблоки", keys.get(1));
        assertEquals("Помидоры", keys.get(2));

    }


//            new Sale(3, 2, 1, 2, "2024-05-02"),  // В Магните 2 помидора
//            new Sale(4, 3, 3, 10, "2024-05-03")  // В Перекрестке 10 яблок

}

package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalyzerTest {

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
        analyzer.getStoreRepo().getSales().add(new Sale(5, 1, 5, 100, LocalDate.parse("2024-05-01")));

        assertTrue(analyzer.getThreeBestSellers().containsKey("Неизвестный товар"));

    }

    @Test
    void getThreeBestSellers_whenSalesListExists_thenReturnMaxThreeTopSoldProducts() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().getProducts().add(new Product(5, null, "Овощи", 120.0));
        analyzer.getStoreRepo().getSales().add(new Sale(5, 1, 5, 100, LocalDate.parse("2024-05-01")));
        List<String> keys = new ArrayList<>(analyzer.getThreeBestSellers().keySet());

        assertEquals(3, keys.size());
        assertEquals("Неизвестный товар", keys.get(0));
        assertEquals("Яблоки", keys.get(1));
        assertEquals("Помидоры", keys.get(2));

    }

    @Test
    void getStoresWithoutFruits_whenNoStoreHasFruits_thenReturnCompleteOriginalList() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setWarehouses(new ArrayList<>(Arrays.asList(
                new Warehouse(1, 1, 50),
                new Warehouse(1, 2, 30),
                new Warehouse(2, 1, 20),
                new Warehouse(3, 2, 100),   // Пусть вместо яблок будут огурцы
                new Warehouse(4, 4, 40)
        )));

        assertEquals(4, analyzer.getStoresWithoutFruits().size());

    }

    @Test
    void getStoresWithoutFruits_whenAllStoresContainFruits_thenReturnEmptyList() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setWarehouses(new ArrayList<>(Arrays.asList(
                new Warehouse(1, 3, 50),
                new Warehouse(1, 3, 30),
                new Warehouse(2, 3, 20),
                new Warehouse(3, 3, 100),   // Пусть вместо яблок будут огурцы
                new Warehouse(4, 3, 40)
        )));

        assertTrue(analyzer.getStoresWithoutFruits().isEmpty());

    }

    @Test
    void getStoresWithoutFruits_whenNoWarehouseExists_thenReturnEmptyList() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setWarehouses(new ArrayList<>(List.of()));

        assertTrue(analyzer.getStoresWithoutFruits().isEmpty());

    }

    @Test
    void getStoresWithoutFruits_whenNoStoreExists_thenReturnEmptyList() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setStores(new ArrayList<>(List.of()));

        assertTrue(analyzer.getStoresWithoutFruits().isEmpty());

    }

    @Test
    void getMayShopsIncome_whenListOfSalesIsEmpty_thenReturnMapOfStoresWithZeroIncome() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setSales(List.of());

        assertTrue(analyzer.getMayShopsIncome().entrySet().stream()
                .allMatch(entry -> entry.getValue() == 0.0));

    }

    @Test
    void getMayShopsIncome_whenMaySalesDoNotExist_thenReturnMapOfStoresWithZeroIncome() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setSales(List.of(
                new Sale(1, 1, 1, 5, LocalDate.parse("2024-07-01")),  // В Пятерочке продано 5 помидоров
                new Sale(2, 1, 2, 3, LocalDate.parse("2024-07-01")),  // 3 огурца
                new Sale(3, 2, 1, 2, LocalDate.parse("2024-07-02")),  // В Магните 2 помидора
                new Sale(4, 3, 3, 10, LocalDate.parse("2024-07-03"))  // В Перекрестке 10 яблок
        ));

        assertTrue(analyzer.getMayShopsIncome().entrySet().stream()
                .allMatch(entry -> entry.getValue() == 0.0));

    }

    @Test
    void getMayShopsIncome_whenMaySalesExist_thenReturnMapOfStoresWithMayIncome() {

        Analyzer analyzer = new Analyzer();

        List<Store> stores = analyzer.getStoreRepo().getStores();

        Map<Store, Double> precalculatedIncomes = new HashMap<>();

        precalculatedIncomes.put(stores.get(0), 870.0);
        precalculatedIncomes.put(stores.get(1), 240.0);
        precalculatedIncomes.put(stores.get(2), 800.0);
        precalculatedIncomes.put(stores.get(3), 0.0);

        assertEquals(precalculatedIncomes.size(), stores.size());

        for (Store store : stores) {
            assertEquals(analyzer.getMayShopsIncome().get(store), precalculatedIncomes.get(store));
        }

    }

    @Test
    void getNeverSoldProducts_whenSalesListIsEmpty_thenReturnOriginalProductList() {

        Analyzer analyzer = new Analyzer();
        analyzer.getStoreRepo().setSales(List.of());

        List<Product> products = analyzer.getStoreRepo().getProducts();
        List<Product> neverSoldProducts = analyzer.getNeverSoldProducts();

        assertEquals(4, neverSoldProducts.size());

        for (Product product : products) {
            assertTrue(neverSoldProducts.stream()
                    .anyMatch(n -> n.getId() == product.getId()));
        }

    }

    @Test
    void getNeverSoldProducts_whenSalesListIsNotEmpty_thenReturnListOfNeverSoldProducts() {

        Analyzer analyzer = new Analyzer();

        List<Product> neverSoldProducts = analyzer.getNeverSoldProducts();

        assertEquals(1, neverSoldProducts.size());
        assertEquals(4, neverSoldProducts.get(0).getId());

    }

    @Test
    void getNeverSoldProducts_whenSalesListHasNoMatchInProducts_thenNameThatProductUnknownAndShowId() {

        Analyzer analyzer = new Analyzer();
        // Избавимся от молока:
        analyzer.getStoreRepo().getSales().add(new Sale(5, 3, 4, 2, LocalDate.parse("2024-05-03")));
        // ...и добавим загадочный продукт №5:
        analyzer.getStoreRepo().getWarehouses().add(new Warehouse(4, 5, 40));

        List<Product> neverSoldProducts = analyzer.getNeverSoldProducts();

        assertEquals(1, neverSoldProducts.size());
        assertEquals("Неизвестный продукт с id = 5", neverSoldProducts.get(0).getName());

    }

    @Test
    void getAvgPriceByCategories_whenProductListIsEmpty_thenReturnEmptyMap() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setProducts(List.of());

        assertTrue(analyzer.getAvgPriceByCategories().isEmpty());

    }

    @Test
    void getAvgPriceByCategories_whenProductListIsNotEmpty_thenReturnMapOfAveragePrices() {

        Analyzer analyzer = new Analyzer();

        Map<String, Double> averages = analyzer.getAvgPriceByCategories();

        assertEquals(3, averages.size());

        assertEquals(105.0, averages.get("Овощи"));
        assertEquals(80.0, averages.get("Фрукты"));
        assertEquals(70.0, averages.get("Молочные продукты"));

    }

    @Test
    void getAvgPriceByCategories_whenAvgPriceHasMoreThanTwoSignsAfterPoint_thenReturnMapOfRoundedAveragePrices() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setProducts(List.of(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Огурцы", "Овощи", 90.0),
                new Product(3, "Картофель", "Овощи", 80.0)));

        Map<String, Double> averages = analyzer.getAvgPriceByCategories();

        assertEquals(96.67, averages.get("Овощи"));

    }

    @Test
    void getTwoDaysNoSalesStores_whenListOfStoresIsEmpty_thenReturnEmptyList() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setStores(List.of());

        assertTrue(analyzer.getTwoDaysNoSalesStores().isEmpty());

    }

    @Test
    void getTwoDaysNoSalesStores_whenListOfSalesIsEmpty_thenReturnEntireOriginalStoreList() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setSales(List.of());

        assertEquals(4, analyzer.getTwoDaysNoSalesStores().size());

    }

    @Test
    void getTwoDaysNoSalesStores_whenListsOfSalesAndStoresExist_thenReturnStoreListOfNoSalesInTwoDays() {

        Analyzer analyzer = new Analyzer();

        analyzer.getStoreRepo().setSales(List.of(
                new Sale(1, 1, 1, 5, LocalDate.parse("2025-08-04")),
                new Sale(2, 1, 2, 3, LocalDate.parse("2025-08-04")),
                new Sale(3, 2, 1, 2, LocalDate.parse("2025-08-04")),
                new Sale(4, 3, 3, 10, LocalDate.parse("2024-05-03"))
        ));

        List<Store> noSaleStore = analyzer.getTwoDaysNoSalesStores();

        assertEquals(2, noSaleStore.size());

        assertTrue((Objects.equals(noSaleStore.get(0).getName(), "Перекресток")) ||
                (Objects.equals(noSaleStore.get(0).getName(), "Дикси")));
        assertTrue((Objects.equals(noSaleStore.get(1).getName(), "Перекресток")) ||
                (Objects.equals(noSaleStore.get(1).getName(), "Дикси")));

    }

}

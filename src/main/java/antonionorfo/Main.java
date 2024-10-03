package antonionorfo;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();

        products.add(new Product(1L, "Hulk", "Books", 5.0));
        products.add(new Product(2L, "Il signore degli anelli", "Books", 34.0));
        products.add(new Product(3L, "Spiderman", "Baby", 20.0));
        products.add(new Product(4L, "DragonBall", "Baby", 1501.0));
        products.add(new Product(5L, "Avengers", "Books", 180.0));
        products.add(new Product(6L, "Il gladiatore", "Boys", 3620.0));
        products.add(new Product(7L, "Noah", "Books", 510.0));
        products.add(new Product(8L, "Io sono leggenda", "Boys", 590.0));
        products.add(new Product(9L, "Il pianeta delle scimmie", "Baby", 12900.0));
        products.add(new Product(10L, "DeadPoll vs Wolverine", "Boys", 1450.0));
        products.add(new Product(11L, "X-Man", "Boys", 2180.0));
        products.add(new Product(12L, "I Puffi", "Baby", 3320.0));


        List<Order> orders = List.of(
                new Order(1L, "Pending", LocalDate.of(2024, 1, 3), List.of(products.get(0), products.get(1)), new Customer(1L, "Frodo", 2)),
                new Order(2L, "Delivered", LocalDate.of(2021, 5, 7), List.of(products.get(2), products.get(3)), new Customer(2L, "Gandalf", 2)),
                new Order(3L, "Pending", LocalDate.of(2023, 8, 5), List.of(products.get(4), products.get(5)), new Customer(3L, "Aragon", 3)),
                new Order(4L, "Delivered", LocalDate.of(2021, 2, 3), List.of(products.get(6), products.get(7)), new Customer(4L, "Legolas", 1)),
                new Order(5L, "Pending", LocalDate.of(2021, 2, 3), List.of(products.get(8), products.get(9)), new Customer(5L, "Ghimili", 2)),
                new Order(6L, "Delivered", LocalDate.of(2021, 2, 3), List.of(products.get(10), products.get(11)), new Customer(6L, "Set", 3))
        );


        Map<Customer, List<Order>> ordersGroupedByCustomer = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));

        System.out.println();
        System.out.println("Ordini raggruppati per cliente:");
        System.out.println();
        ordersGroupedByCustomer.forEach((customer, ordersList) -> {
            System.out.println("Cliente: " + customer);
            ordersList.forEach(order -> System.out.println("  Ordine: " + order));
        });


        // dato un elenco di ordini , calcola il totale delle vendite per ogni cliente utilizzando stream e lamba expreessiones crea una mappa in cui la vhiace e' il cliente e il valoe e' l importo totale dei suoi acquisti

        Map<Customer, Double> totalSalesByCustomer = orders.stream()
                .collect(Collectors.groupingBy(
                        Order -> Order.getCustomer(),
                        Collectors.summingDouble(order -> order.getProducts().stream()
                                .mapToDouble(Product -> Product.getPrice())
                                .sum())
                ));

        System.out.println();
        System.out.println("Totale vendite per cliente:");
        System.out.println();
        totalSalesByCustomer.forEach((customer, totalSales) ->
                System.out.println("Cliente: " + customer + ", Totale vendite: " + totalSales));


        //dato un elenco di prodotti , trova i prodotti piu costosi utilizzando Stream e lamba expressions

        Optional<Product> mostExpensiveProduct = products.stream()
                .max(Comparator.comparingDouble(Product -> Product.getPrice()));

        System.out.println();
        System.out.println("Prodotto più costoso:");
        System.out.println();
        mostExpensiveProduct.ifPresent(product -> System.out.println(product));


        //dato un elenco di ordini , calcola la media degli importi degli ordini utlizziando stream e lamba expressione

        double averageOrderAmount = orders.stream()
                .collect(Collectors.averagingDouble(order -> order.getProducts().stream()
                        .mapToDouble(Product -> Product.getPrice())
                        .sum()));

        System.out.println();
        System.out.println("Media degli importi degli ordini:");
        System.out.println(averageOrderAmount);


        //dato un elenco di prodotti , raggruppa i prodotti per categoria e calcola la somma degli importi per ogni categoria utilizzando stream e lamba expressions

        Map<String, Double> totalSalesByCategory = products.stream()
                .collect(Collectors.groupingBy(productss -> productss.getCategory(), Collectors.summingDouble(Product -> Product.getPrice())));


        System.out.println();
        System.out.println("Totale vendite per categoria:");
        System.out.println();
        totalSalesByCategory.forEach((category, totalSales) ->
                System.out.println("Categoria: " + category + ", Totale vendite: " + totalSales));


    }
}
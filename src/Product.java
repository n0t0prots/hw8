import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Product implements Comparable<Product> {
    private final String id;
    private final String type;
    private double price;
    private final boolean isDiscountChance;
    private final LocalDate dateProductAdded;

    public Product(String id, String type, double price, boolean isDiscountChance, LocalDate dateProductAdded) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.isDiscountChance = isDiscountChance;
        this.dateProductAdded = dateProductAdded;
    }

    public static List<Product> getFilterByTypePrice(List<Product> products) {
        List<Product> productsFiltered = products.stream().filter(product -> product.type
                        .equals("Book") && product.price > 250)
                .collect(Collectors.toList());
        System.out.println(productsFiltered);
        return productsFiltered;
    }

    public static List<Product> getFilterByTypeDiscount(List<Product> products) {
        List<Product> discountProducts = products.stream().filter(product -> product.type
                        .equals("Book") && product.isDiscountChance)
                .peek(product -> product.price = product.price * 0.9).collect(Collectors.toList());
        System.out.println(discountProducts);
        return discountProducts;
    }

    public static Product getCheapestBook(List<Product> products, String checkedType) {
        Product cheapestProduct = products.stream().filter(product -> product.type.equals(checkedType))
                .min(Product::compareTo)
                .orElseThrow(() -> new ProductNotFoundException("Product " + checkedType + " not found"));
        System.out.println(cheapestProduct);
        return cheapestProduct;
    }

    public static List<Product> getFilterByAddedDate(List<Product> products) {
        List<Product> filterByAddDateProducts = products.stream().sorted(new ProductComparator().reversed())                                                      //Comparator.comparing(Product::getDateProductAdded)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(filterByAddDateProducts);
        return filterByAddDateProducts;
    }

    public static double getTotalPrice(List<Product> products) {
        LocalDate now = LocalDate.now();
        double totalPrice = products.stream().filter(product -> product.dateProductAdded.getYear() == now.getYear()
                        && product.type.equals("Book") && product.price < 75)
                .mapToDouble(product -> product.price)
                .sum();
        System.out.println(totalPrice);
        return totalPrice;
    }

    public static Map<String, List<Product>> getSortByType(List<Product> products) {
        Map<String, List<Product>> mapSortedByType = products.stream()
                .collect(Collectors.groupingBy(Product::getId));
        String mapAsString = mapSortedByType.keySet().stream()
                .map(key -> '\n' + "    " + key + "," + '\n' + mapSortedByType.get(key))
                .collect(Collectors.joining(",", "{", "\n}"));
        System.out.println(mapAsString);
        return mapSortedByType;
    }

    public LocalDate getDateProductAdded() {
        return dateProductAdded;
    }

    @Override
    public int compareTo(Product o) {
        return Double.compare(this.price, o.price);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return '\n' + "{type: " + type +
                ", price:" + price +
                ", isDiscountChance:" + isDiscountChance +
                ", dateProductAdded:" + dateProductAdded + "}";
    }
}
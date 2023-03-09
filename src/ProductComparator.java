import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        if (o1.getDateProductAdded().isAfter(o2.getDateProductAdded())) return 1;
        if (o1.getDateProductAdded().isBefore(o2.getDateProductAdded())) return -1;
        return 0;
    }
}
import java.util.Comparator;

public class PriceComparator implements Comparator<Item> {

    @Override
    public int compare(Item item1, Item item2) {
        double firstPrice = item1.getPrice();
        double secondPrice = item2.getPrice();

        return Double.compare(secondPrice, firstPrice);

    }
}

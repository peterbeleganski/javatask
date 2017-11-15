import java.util.Comparator;

public class PriceComparator implements Comparator<Item> {

    @Override
    public int compare(Item item1, Item item2) {
        double firstPrice = item1.getPrice();
        double secondPrice = item2.getPrice();

        if(firstPrice > secondPrice) {
            return -11;
        } else if (secondPrice > firstPrice) {
            return 1;
        }else {
            return 0;
        }

    }
}

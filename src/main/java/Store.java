import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Store {
    public static void main(String... args) {
        List<Item> results = new ArrayList<>();
        List<Item> items = readDataFromCSV("input-bread.csv");

        parseAndSetValues(results, items);

        printResults(results);
    }

    private static void parseAndSetValues(List<Item> results, List<Item> items) {
        for (Item item : items) {
            String itemKind = items.get(1).getType().getTypeName();

            if(item.getQuality().toLowerCase().equals("quality")) {
                continue;
            }
            int quality = Integer.parseInt(item.getQuality());
            String serialNumber = item.getSerialNumber();
            String itemTypeParameter = item.getType().getParameter();
            double price = 0.0;

            if(itemKind.equals("Bread")) {
                if(quality == 1) {
                    price = 2.0;
                } else if (quality == 2) {
                    price = 1.5;
                } else if ( quality == 3) {
                    price =1.0;
                }

                item.setPrice(price);
                results.add(item);
            }
        }
    }

    private static void printResults(List<Item> results) {
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }
    }

    private static List<Item> readDataFromCSV(String fileName) {
        List<Item> items = new ArrayList<Item>();
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                Item item = createItem(attributes);
                items.add(item);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return items;
    }

    private static Item createItem(String[] metadata) {
        String serialNumber = metadata[0];
        String type = metadata[1];
        String quality = metadata[2];
        String parameter = metadata[3];
        ItemType it = new ItemType();
        it.setTypeName(type);
        it.setParameter(parameter);

        return new Item(serialNumber,it, quality, 0.0);
    }
}



import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Store {
    public static void main(String... args) {
        List<Item> results = new ArrayList<>();
        List<Item> tomatoItems = readDataFromCSV("input-tomato.csv");
        List<Item> cheeseItems = readDataFromCSV("input-cheese.csv");
        List<Item> breadItems = readDataFromCSV("input-bread.csv");

        parseAndSortCollections(results, tomatoItems, cheeseItems, breadItems);
        printInstructions();
        inputOptionsCheck(results, tomatoItems, cheeseItems, breadItems);
    }

    private static void parseAndSortCollections(List<Item> results, List<Item> tomatoItems, List<Item> cheeseItems, List<Item> breadItems) {
        parseAndSetValues(results, breadItems);
        parseAndSetValues(results, cheeseItems);
        parseAndSetValues(results, tomatoItems);

        Collections.sort(results, new PriceComparator());
        Collections.sort(breadItems, new PriceComparator());
        Collections.sort(cheeseItems, new PriceComparator());
        Collections.sort(tomatoItems, new PriceComparator());
    }

    private static void printInstructions() {
        System.out.println("Choose option to proceed:");
        System.out.println("1 - Top 3 the most expensive items");
        System.out.println("2 - The count of all parsed items");
        System.out.println("3 - All bread items with Barley flour");
        System.out.println("10 - Exit the input menu");
        System.out.println("-------- ENTER YOUR OPTION BELOW --------");
    }

    private static void inputOptionsCheck(List<Item> results, List<Item> tomatoItems, List<Item> cheeseItems, List<Item> breadItems) {
        Scanner sc = new Scanner(System.in);

        int option = Integer.parseInt(sc.nextLine());

        while (option != 10) {
            switch (option) {
                case 1: {
                    printTopThreePrices(tomatoItems, cheeseItems, breadItems);
                    break;
                }
                case 2: {
                    printSizeOfAllItems(results);
                    break;
                }
                case 3: {
                    results.stream()
                            .filter(i -> i.getType().getParameter().equals("Barley"))
                            .forEach(item -> System.out.println(item));
                    break;
                }
            }
            option = Integer.parseInt(sc.nextLine());
        }


    }

    private static void printSizeOfAllItems(List<Item> results) {
        int size = results.size();
        System.out.println("The count of all items is: " + size);
    }

    private static void printTopThreePrices(List<Item> tomatoItems, List<Item> cheeseItems, List<Item> breadItems) {
        System.out.println("Top 3");
        System.out.println(tomatoItems.get(0));
        System.out.println(cheeseItems.get(0));
        System.out.println(breadItems.get(0));
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
            } else if (itemKind.equals("Cheese")) {
                if(quality == 1) {
                    price = 15.00;
                } else if (quality == 2) {
                    price = 12.00;
                } else if ( quality == 3) {
                    price = 6.00;
                }
            } else if (itemKind.equals("Tomato")) {
                if(quality == 1) {
                    price = 6.00;
                } else if (quality == 2) {
                    price = 4.50;
                } else if ( quality == 3) {
                    price = 2;
                }
            }

            item.setPrice(price);
            results.add(item);
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



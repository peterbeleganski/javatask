import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Collections.sort;


public class Store {
    public static void main(String... args) {
        List<Item> results = new ArrayList<>();
        List<Item> tomatoItems = readDataFromCSV("input-tomato.csv", "default");
        List<Item> cheeseItems = readDataFromCSV("input-cheese.csv", "default");
        List<Item> breadItems = readDataFromCSV("input-bread.csv", "default");
        List<Item> shampooItems = readDataFromCSV("input-shampoo.csv", "shampoo");
        List<Item> icecreamItems = readDataFromCSV("input-icecream.csv", "icecream");

        parseAndSortCollections(results, tomatoItems, cheeseItems, breadItems, shampooItems, icecreamItems);
        printInstructions();
        inputOptionsCheck(results, tomatoItems, cheeseItems, breadItems, shampooItems, icecreamItems);

    }

    private static void parseAndSortCollections(List<Item> results, List<Item> tomatoItems,
                                                List<Item> cheeseItems, List<Item> breadItems,
                                                List<Item> shampooItems, List<Item> icecreamItems) {
        parseAndSetValues(results, breadItems);
        parseAndSetValues(results, cheeseItems);
        parseAndSetValues(results, tomatoItems);
        parseAndSetValues(results, shampooItems);
        parseAndSetValues(results, icecreamItems);

        sort(breadItems, new PriceComparator());
        sort(cheeseItems, new PriceComparator());
        sort(tomatoItems, new PriceComparator());
        sort(shampooItems, new PriceComparator());
        sort(icecreamItems, new PriceComparator());
        sort(results, new PriceComparator());
    }

    private static void printInstructions() {
        System.out.println("Choose option to proceed:");
        System.out.println("1 - Top 3 the most expensive items");
        System.out.println("2 - The count of all parsed items");
        System.out.println("3 - All bread items with Barley flour");
        System.out.println("4 - Print all items with quality N# 3");
        System.out.println("5 - Print all items with serial-number [1000-2000]");
        System.out.println("10 - Exit the input menu");
        System.out.println("-------- ENTER YOUR OPTION BELOW --------");
    }

    private static void inputOptionsCheck(List<Item> results, List<Item> tomatoItems,
                                          List<Item> cheeseItems, List<Item> breadItems,
                                          List<Item> shampooItems, List<Item> icecreamItems) {
        Scanner sc = new Scanner(System.in);

        int option = Integer.parseInt(sc.nextLine());

        while (option != 10) {
            switch (option) {
                case 1: {
                    printTopThreePrices(tomatoItems, cheeseItems, breadItems, shampooItems, icecreamItems);
                    break;
                }
                case 2: {
                    printSizeOfAllItems(results);
                    break;
                }
                case 3: {
                    results.stream()
                            .filter(i -> i.getType().getParameter().equals("Barley"))
                            .forEach(System.out::println);
                    break;
                }
                case 4: {
                    printItemsWithLowestQuality(results);
                    break;
                }
                case 5: {
                    results.stream()
                            .filter(i -> Integer.parseInt(i.getSerialNumber()) >= 1000 && Integer.parseInt(i.getSerialNumber()) <= 2000)
                            .forEach(System.out::println);
                }
            }
            option = Integer.parseInt(sc.nextLine());
        }


    }

    private static void printItemsWithLowestQuality(List<Item> results) {
        System.out.println("Items with quality N# 3:");
        results.stream()
                .filter(i -> Integer.parseInt(i.getQuality()) == 3)
                .forEach(System.out::println);
    }

    private static void printSizeOfAllItems(List<Item> results) {
        int size = results.size();
        System.out.println("The count of all items is: " + size);
    }

    private static void printTopThreePrices(List<Item> tomatoItems, List<Item> cheeseItems, List<Item> breadItems, List<Item> shampooItems, List<Item> icecreamItems) {
        System.out.println("Top 3");

        List<Item> itemsToSort = new ArrayList<>();

        itemsToSort.add(tomatoItems.get(0));
        itemsToSort.add(cheeseItems.get(0));
        itemsToSort.add(breadItems.get(0));
        itemsToSort.add(shampooItems.get(0));
        itemsToSort.add(icecreamItems.get(0));

        sort(itemsToSort, new PriceComparator());

        for (int i = 0; i < 3; i++) {
            System.out.println(itemsToSort.get(i));
        }
    }

    private static void parseAndSetValues(List<Item> results, List<Item> items) {
        for (Item item : items) {
            String itemKind = items.get(1).getType().getTypeName();
            // System.out.println(itemKind);
            if(item.getQuality().toLowerCase().equals("quality")) {
                continue;
            }
            int quality = Integer.parseInt(item.getQuality());
            String serialNumber = item.getSerialNumber();
            String itemTypeParameter = item.getType().getParameter();
            double price = 0.0;
            // System.out.println(itemKind);
            switch (itemKind) {
                case "Bread":
                    if (quality == 1) {
                        price = 2.0;
                    } else if (quality == 2) {
                        price = 1.5;
                    } else if (quality == 3) {
                        price = 1.0;
                    }
                    break;
                case "Cheese":
                    if (quality == 1) {
                        price = 15.00;
                    } else if (quality == 2) {
                        price = 12.00;
                    } else if (quality == 3) {
                        price = 6.00;
                    }
                    break;
                case "Tomato":
                    if (quality == 1) {
                        price = 6.00;
                    } else if (quality == 2) {
                        price = 4.50;
                    } else if (quality == 3) {
                        price = 2;
                    }
                    break;
                case "Shampoo":
                    if (quality == 1) {
                        price = 12.00;
                    } else if (quality == 2) {
                        price = 8.00;
                    } else if (quality == 3) {
                        price = 4.50;
                    }
                    break;
                case "ice-cream":
                    if (quality == 1) {
                        price = 15.00;
                    } else if (quality == 2) {
                        price = 12.00;
                    } else if (quality == 3) {
                        price = 6.00;
                    }
            }

            item.setPrice(price);
            results.add(item);
        }
    }

    private static void printResults(List<Item> results) {
        for (Item result : results) {
            System.out.println(result);
        }
    }

    private static List<Item> readDataFromCSV(String fileName, String type) {
        List<Item> items = new ArrayList<Item>();
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            Item item = null;
            while (line != null) {
                String[] attributes = line.split(",");
                if(type.equals("default")) {
                    item = createItem(attributes);
                }else {
                    if (type.equals("shampoo")) {
                        item = createShampooItem(attributes);
                    }else {
                        item = createIceCreamItem(attributes);
                    }

                }
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

    private static Item createShampooItem(String[] metadata) {
        String serialNumber = metadata[0];
        String type = "Shampoo";
        String quality = metadata[1];
        String parameter = metadata[2];
        ItemType it = new ItemType();
        it.setTypeName(type);
        it.setParameter(parameter);
        return new Item(serialNumber, it, quality, 0.0);
    }

    private static Item createIceCreamItem(String[] metadata) {
        String serialNumber = metadata[0];
        String type = "ice-cream";
        String quality = metadata[1];
        String parameter = metadata[2];
        ItemType it = new ItemType();
        it.setTypeName(type);
        it.setParameter(parameter);
        return new Item(serialNumber, it, quality, 0.0);
    }
}



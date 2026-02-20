import java.util.Scanner;

public class SmartCityApp {

    private static final String SEPARATOR = "============================================================";

    private static final String DIVIDER = "------------------------------------------------------------";

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            BinarySearchTree bst = new BinarySearchTree();

            System.out.println(SEPARATOR);
            System.out.println("  Smart City Route Planner  –  Initialising...");
            System.out.println(SEPARATOR);
            System.out.println("Pre-loading cities into BST:");

            String[] seedCities = {
                    "Colombo", "Kandy", "Galle", "Matara",
                    "Jaffna", "Anuradhapura", "Trincomalee", "Badulla"
            };
            for (String city : seedCities) {
                bst.insert(city);
            }

            CityGraph graph = new CityGraph(bst);

            System.out.println();
            System.out.println("Initialisation complete. Type a menu number to begin.");

            boolean running = true;
            while (running) {
                printMenu();
                int choice = readMenuChoice(scanner, 1, 9);

                System.out.println(DIVIDER);

                switch (choice) {
                    case 1 -> handleAddToBST(scanner, bst);
                    case 2 -> handleAddLocation(scanner, graph);
                    case 3 -> handleRemoveLocation(scanner, graph);
                    case 4 -> handleAddRoad(scanner, graph);
                    case 5 -> handleRemoveRoad(scanner, graph);
                    case 6 -> graph.displayConnections();
                    case 7 -> handleBFSTraversal(scanner, graph);
                    case 8 -> bst.printInOrder();
                    case 9 -> {
                        System.out.println("Exiting Smart City Route Planner. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("[Error] Unexpected choice. Please try again.");
                }

                if (running) {
                    System.out.println();
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println(SEPARATOR);
        System.out.println("  SMART CITY ROUTE PLANNER  –  Main Menu");
        System.out.println(SEPARATOR);
        System.out.println("  1. Add city to BST (register as valid location)");
        System.out.println("  2. Add location to Graph");
        System.out.println("  3. Remove location from Graph");
        System.out.println("  4. Add road between two locations");
        System.out.println("  5. Remove road between two locations");
        System.out.println("  6. Display Map (all connections)");
        System.out.println("  7. BFS Traversal (show reachable cities)");
        System.out.println("  8. Show BST cities (sorted)");
        System.out.println("  9. Exit");
        System.out.println(SEPARATOR);
        System.out.print("Enter choice [1-9]: ");
    }

    private static void handleAddToBST(Scanner scanner, BinarySearchTree bst) {
        String city = readCityName(scanner, "Enter city name to add to BST");
        bst.insert(city);
    }

    private static void handleAddLocation(Scanner scanner, CityGraph graph) {
        String city = readCityName(scanner, "Enter city name to add to Graph");
        graph.addLocation(city);
    }

    private static void handleRemoveLocation(Scanner scanner, CityGraph graph) {
        String city = readCityName(scanner, "Enter city name to remove from Graph");
        graph.removeLocation(city);
    }

    private static void handleAddRoad(Scanner scanner, CityGraph graph) {
        String source = readCityName(scanner, "Enter SOURCE city");
        String destination = readCityName(scanner, "Enter DESTINATION city");
        graph.addRoad(source, destination);
    }

    private static void handleRemoveRoad(Scanner scanner, CityGraph graph) {
        String source = readCityName(scanner, "Enter SOURCE city");
        String destination = readCityName(scanner, "Enter DESTINATION city");
        graph.removeRoad(source, destination);
    }

    private static void handleBFSTraversal(Scanner scanner, CityGraph graph) {
        String start = readCityName(scanner, "Enter START city for BFS traversal");
        graph.showAllPaths(start);
    }

    private static int readMenuChoice(Scanner scanner, int min, int max) {
        while (true) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                System.out.print("[Input] Please enter a number ["
                        + min + "-" + max + "]: ");
                continue;
            }

            try {
                int choice = Integer.parseInt(line);

                if (choice < min || choice > max) {
                    System.out.print("[Input] Choice must be between "
                            + min + " and " + max + ". Try again: ");
                    continue;
                }

                return choice;

            } catch (NumberFormatException e) {
                System.out.print("[Input] \"" + line
                        + "\" is not a valid number. Enter a number ["
                        + min + "-" + max + "]: ");
            }
        }
    }

    private static String readCityName(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("[Input] City name cannot be blank. Please try again.");
        }
    }
}

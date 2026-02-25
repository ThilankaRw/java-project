import java.util.Random;
import java.util.Scanner;

/**
 * DatasetManager - Data Sorter Module
 *
 * Handles data input for the sorting algorithms.
 * Provides two ways to obtain an integer array:
 * 1. Manual input from the user via the console.
 * 2. Automatic generation of a random dataset of a given size.
 */
public class DatasetManager {

    // Scanner for reading console input (shared across calls)
    private Scanner scanner;

    /**
     * Constructor – initialises the shared Scanner instance.
     */
    public DatasetManager() {
        this.scanner = new Scanner(System.in);
    }

    // ==================== MANUAL INPUT ====================

    /**
     * Prompts the user to enter integers one by one via the console.
     *
     * Workflow:
     * 1. Ask how many numbers the user wants to enter.
     * 2. Read that many integers, validating each entry.
     * 3. If the user types something that is not a valid integer,
     * display an error message and ask for that entry again.
     *
     * @return an int array containing the numbers entered by the user
     */
    public int[] getManualInput() {
        int size = 0;

        // --- Step 1: Get the count of numbers the user wants to enter ---
        while (true) {
            System.out.print("How many numbers would you like to enter? ");
            try {
                size = Integer.parseInt(scanner.nextLine().trim());

                // Validate that the size is positive
                if (size <= 0) {
                    System.out.println("Error: Please enter a positive number greater than 0.");
                    continue;
                }
                break; // Valid size received

            } catch (NumberFormatException e) {
                // The user typed something that is not a valid integer
                System.out.println("Error: Invalid input. Please enter a valid whole number.");
            }
        }

        int[] arr = new int[size];

        // --- Step 2: Read each number with error handling ---
        System.out.println("Enter " + size + " integer(s), one per line:");

        for (int i = 0; i < size; i++) {
            while (true) {
                System.out.print("  Number " + (i + 1) + ": ");
                String input = scanner.nextLine().trim();
                try {
                    arr[i] = Integer.parseInt(input);
                    break; // Valid number received, move to the next

                } catch (NumberFormatException e) {
                    // The user typed something that is not a valid integer
                    System.out.println("  Error: '" + input + "' is not a valid integer. Please try again.");
                }
            }
        }

        System.out.println("Successfully read " + size + " number(s).");
        return arr;
    }

    // ================= RANDOM DATASET GENERATION =================

    /**
     * Generates an array of random integers of the specified size.
     *
     * The random values are in the range [0, 9999] by default,
     * providing a broad-enough spread for demonstration and testing.
     *
     * @param size the number of random integers to generate (must be &gt; 0)
     * @return an int array filled with random integers
     * @throws IllegalArgumentException if size is less than or equal to 0
     */
    public int[] generateRandomDataset(int size) {
        // Validate the requested size
        if (size <= 0) {
            throw new IllegalArgumentException("Dataset size must be greater than 0. Received: " + size);
        }

        Random random = new Random();
        int[] arr = new int[size];

        // Fill the array with random integers between 0 and 9999
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(10000); // 0 (inclusive) to 10000 (exclusive)
        }

        System.out.println("Generated a random dataset of " + size + " number(s).");
        return arr;
    }
}

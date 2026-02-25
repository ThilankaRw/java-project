import java.util.Arrays;
import java.util.Scanner;

/**
 * DataSorterApp - Main Menu-Driven Application for the Data Sorter Module
 *
 * This class ties together the DatasetManager and SortingAlgorithms classes.
 * It lets the user:
 * 1. Choose a data-input method (manual entry or random generation).
 * 2. Run Bubble Sort, Merge Sort, and Quick Sort on identical copies of the
 * data.
 * 3. Measure each algorithm's execution time using System.nanoTime().
 * 4. Display the sorted output.
 * 5. Print a formatted performance-comparison table.
 */
public class DataSorterApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DatasetManager datasetManager = new DatasetManager();
        SortingAlgorithms sorter = new SortingAlgorithms();

        // ===================== WELCOME BANNER =====================
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║        DATA SORTER – Algorithm Benchmark    ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();

        // ================ STEP 1: CHOOSE INPUT METHOD =================
        int[] originalData = null;

        while (originalData == null) {
            System.out.println("How would you like to provide the dataset?");
            System.out.println("  [1] Enter numbers manually");
            System.out.println("  [2] Generate a random dataset");
            System.out.print("Your choice (1 or 2): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    // Manual input via DatasetManager
                    originalData = datasetManager.getManualInput();
                    break;

                case "2":
                    // Random generation – ask for the desired size
                    int size = 0;
                    while (size <= 0) {
                        System.out.print("Enter the number of random integers to generate: ");
                        try {
                            size = Integer.parseInt(scanner.nextLine().trim());
                            if (size <= 0) {
                                System.out.println("Error: Size must be greater than 0.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Invalid input. Please enter a valid whole number.");
                        }
                    }
                    originalData = datasetManager.generateRandomDataset(size);
                    break;

                default:
                    System.out.println("Error: Invalid choice. Please enter 1 or 2.\n");
                    break;
            }
        }

        // Display the original (unsorted) dataset
        System.out.println("\n──────────── Original Dataset ────────────");
        System.out.println(Arrays.toString(originalData));
        System.out.println("Dataset size: " + originalData.length);

        // ============ STEP 2: PREPARE COPIES FOR FAIR COMPARISON ============
        // Each algorithm receives its own independent copy so that one
        // sort does not affect the input of another.
        int[] bubbleData = Arrays.copyOf(originalData, originalData.length);
        int[] mergeData = Arrays.copyOf(originalData, originalData.length);
        int[] quickData = Arrays.copyOf(originalData, originalData.length);

        // ============ STEP 3: RUN & TIME EACH ALGORITHM ============

        // --- Bubble Sort ---
        long bubbleStart = System.nanoTime();
        sorter.bubbleSort(bubbleData);
        long bubbleTime = System.nanoTime() - bubbleStart;

        // --- Merge Sort ---
        long mergeStart = System.nanoTime();
        sorter.mergeSort(mergeData);
        long mergeTime = System.nanoTime() - mergeStart;

        // --- Quick Sort ---
        long quickStart = System.nanoTime();
        sorter.quickSort(quickData);
        long quickTime = System.nanoTime() - quickStart;

        // ============ STEP 4: DISPLAY SORTED OUTPUT ============
        System.out.println("\n──────────── Sorted Output ────────────");
        System.out.println("Bubble Sort : " + Arrays.toString(bubbleData));
        System.out.println("Merge Sort  : " + Arrays.toString(mergeData));
        System.out.println("Quick Sort  : " + Arrays.toString(quickData));

        // ============ STEP 5: PERFORMANCE COMPARISON TABLE ============
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║            PERFORMANCE COMPARISON TABLE                 ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.printf("║  %-20s │ %18s │ %10s ║%n", "Algorithm", "Time (ns)", "Time (ms)");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.printf("║  %-20s │ %,18d │ %10.4f ║%n", "Bubble Sort", bubbleTime, bubbleTime / 1_000_000.0);
        System.out.printf("║  %-20s │ %,18d │ %10.4f ║%n", "Merge Sort", mergeTime, mergeTime / 1_000_000.0);
        System.out.printf("║  %-20s │ %,18d │ %10.4f ║%n", "Quick Sort", quickTime, quickTime / 1_000_000.0);
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        // Determine and display the fastest algorithm
        String fastest;
        long fastestTime;

        if (bubbleTime <= mergeTime && bubbleTime <= quickTime) {
            fastest = "Bubble Sort";
            fastestTime = bubbleTime;
        } else if (mergeTime <= bubbleTime && mergeTime <= quickTime) {
            fastest = "Merge Sort";
            fastestTime = mergeTime;
        } else {
            fastest = "Quick Sort";
            fastestTime = quickTime;
        }

        System.out.printf("%n★  Fastest algorithm: %s (%,d ns)%n", fastest, fastestTime);
        System.out.println("\nThank you for using the Data Sorter!");

        scanner.close();
    }
}

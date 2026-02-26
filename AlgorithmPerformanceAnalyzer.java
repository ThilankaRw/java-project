import java.util.Arrays;
import java.util.Random;

/**
 * Module 3: AlgorithmPerformanceAnalyzer
 *
 * This class benchmarks Binary Search and Quick Sort across three dataset sizes
 * (100, 500, and 1000 elements) using both sorted and unsorted arrays.
 * Execution times are measured with System.nanoTime() for high-resolution
 * timing.
 */
public class AlgorithmPerformanceAnalyzer {

    // -------------------------------------------------------------------------
    // Array Generation Methods
    // -------------------------------------------------------------------------

    /**
     * Generates an unsorted (randomly shuffled) integer array of the given size.
     * Values are drawn from the range [0, size * 10) so the range scales with size.
     *
     * @param size the number of elements in the array
     * @return a new int[] filled with random values
     */
    public int[] generateUnsortedArray(int size) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size * 10); // values in [0, size*10)
        }
        return arr;
    }

    /**
     * Generates a sorted integer array of the given size.
     * The array is first filled with random values, then sorted in ascending order.
     *
     * @param size the number of elements in the array
     * @return a new int[] sorted in ascending order
     */
    public int[] generateSortedArray(int size) {
        int[] arr = generateUnsortedArray(size);
        Arrays.sort(arr); // sort using Java's built-in sort for setup purposes
        return arr;
    }

    // -------------------------------------------------------------------------
    // Binary Search Algorithm
    // -------------------------------------------------------------------------

    /**
     * Performs an iterative Binary Search on a sorted array.
     *
     * Precondition: the array must be sorted in ascending order.
     * Time complexity: O(log n)
     *
     * @param sortedArr the sorted integer array to search
     * @param target    the value to find
     * @return the index of the target if found, or -1 if not present
     */
    public int binarySearch(int[] sortedArr, int target) {
        int low = 0;
        int high = sortedArr.length - 1;

        while (low <= high) {
            // Avoid potential overflow compared to (low + high) / 2
            int mid = low + (high - low) / 2;

            if (sortedArr[mid] == target) {
                return mid; // target found at index mid
            } else if (sortedArr[mid] < target) {
                low = mid + 1; // target is in the right half
            } else {
                high = mid - 1; // target is in the left half
            }
        }

        return -1; // target not found
    }

    // -------------------------------------------------------------------------
    // Quick Sort Algorithm (reused from Module 2 – SortingAlgorithms)
    // -------------------------------------------------------------------------

    /**
     * Public entry point for Quick Sort.
     * Sorts the given array in place in ascending order.
     *
     * Time complexity: O(n log n) average, O(n²) worst case.
     *
     * @param arr the integer array to sort
     */
    public void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return; // nothing to sort
        }
        quickSortRecursive(arr, 0, arr.length - 1);
    }

    /**
     * Recursive helper for Quick Sort.
     * Partitions the sub-array arr[low..high] around a pivot and recursively
     * sorts each partition.
     *
     * @param arr  the array being sorted
     * @param low  start index of the sub-array (inclusive)
     * @param high end index of the sub-array (inclusive)
     */
    private void quickSortRecursive(int[] arr, int low, int high) {
        if (low < high) {
            // Place the pivot in its correct sorted position
            int pivotIndex = partition(arr, low, high);
            // Recursively sort the left partition (elements < pivot)
            quickSortRecursive(arr, low, pivotIndex - 1);
            // Recursively sort the right partition (elements > pivot)
            quickSortRecursive(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Partitions arr[low..high] using the last element as the pivot.
     * All elements smaller than or equal to the pivot are moved to its left;
     * all larger elements are moved to its right.
     *
     * @param arr  the array being partitioned
     * @param low  start index of the sub-array
     * @param high end index of the sub-array (pivot position)
     * @return the final sorted index of the pivot element
     */
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // choose the last element as pivot
        int i = low - 1; // i tracks the boundary of elements <= pivot

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Place pivot in its correct position
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1; // return the pivot's final index
    }

    // -------------------------------------------------------------------------
    // Performance Measurement Helper
    // -------------------------------------------------------------------------

    /**
     * Measures the execution time of Quick Sort on a copy of the provided array.
     * A fresh copy is made so the original array is not modified.
     *
     * @param arr the array to sort (not modified)
     * @return elapsed time in nanoseconds
     */
    public long measureQuickSortTime(int[] arr) {
        int[] copy = Arrays.copyOf(arr, arr.length); // work on a copy
        long start = System.nanoTime();
        quickSort(copy);
        return System.nanoTime() - start;
    }

    /**
     * Measures the execution time of Binary Search on a sorted array.
     * The target chosen is the element at the midpoint of the array to represent
     * an element that is guaranteed to be present.
     *
     * @param sortedArr the sorted array to search
     * @return elapsed time in nanoseconds
     */
    public long measureBinarySearchTime(int[] sortedArr) {
        // Search for the middle element (guaranteed hit) to get a meaningful timing
        int target = sortedArr[sortedArr.length / 2];
        long start = System.nanoTime();
        binarySearch(sortedArr, target);
        return System.nanoTime() - start;
    }

    // -------------------------------------------------------------------------
    // Main – Benchmark Runner
    // -------------------------------------------------------------------------

    /**
     * Entry point. Runs the performance analysis across three input sizes:
     * 100, 500, and 1000 elements. For each size, both sorted and unsorted
     * arrays are generated and both algorithms are timed and reported.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        AlgorithmPerformanceAnalyzer analyzer = new AlgorithmPerformanceAnalyzer();

        // The three dataset sizes required for this module
        int[] sizes = { 100, 500, 1000 };

        System.out.println("============================================================");
        System.out.println("       MODULE 3 – Algorithm Performance Analyzer");
        System.out.println("============================================================");

        for (int size : sizes) {

            System.out.println("\n------------------------------------------------------------");
            System.out.printf("  Dataset Size: %d elements%n", size);
            System.out.println("------------------------------------------------------------");

            // --- Generate arrays ---
            int[] unsortedArray = analyzer.generateUnsortedArray(size);
            int[] sortedArray = analyzer.generateSortedArray(size);

            // ---- Quick Sort on unsorted input ----
            long qsUnsortedTime = analyzer.measureQuickSortTime(unsortedArray);

            // ---- Quick Sort on already-sorted input ----
            long qsSortedTime = analyzer.measureQuickSortTime(sortedArray);

            // ---- Binary Search on sorted input ----
            // (Binary Search requires a sorted array as a precondition)
            long bsTime = analyzer.measureBinarySearchTime(sortedArray);

            // --- Print results ---
            System.out.printf("  %-35s : %,15d ns%n",
                    "Quick Sort (unsorted input)", qsUnsortedTime);
            System.out.printf("  %-35s : %,15d ns%n",
                    "Quick Sort (sorted input)", qsSortedTime);
            System.out.printf("  %-35s : %,15d ns%n",
                    "Binary Search (sorted array)", bsTime);

            // --- Demonstrate correctness: verify sorted arrays ---
            int[] qsResult = Arrays.copyOf(unsortedArray, unsortedArray.length);
            analyzer.quickSort(qsResult);
            int targetValue = sortedArray[size / 2];
            int foundIndex = analyzer.binarySearch(sortedArray, targetValue);

            System.out.printf("%n  [Correctness] Quick Sort produced a sorted array: %b%n",
                    isSorted(qsResult));
            System.out.printf("  [Correctness] Binary Search for value %d found at index %d: %b%n",
                    targetValue, foundIndex, foundIndex != -1);
        }

        System.out.println("\n============================================================");
        System.out.println("                  Benchmark Complete");
        System.out.println("============================================================");
    }

    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------

    /**
     * Checks whether an integer array is sorted in non-decreasing order.
     *
     * @param arr the array to check
     * @return true if sorted, false otherwise
     */
    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}

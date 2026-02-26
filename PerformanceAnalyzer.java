import java.util.Random;

/**
 * PerformanceAnalyzer – Module 3: Algorithm Performance Analyzer
 *
 * Automatically benchmarks two classic algorithms across three dataset sizes
 * (100, 500, and 1000 elements) and prints a formatted comparison table.
 *
 * Algorithms included:
 * • Quick Sort – average time complexity O(n log n)
 * • Binary Search – time complexity O(log n)
 */
public class PerformanceAnalyzer {

    // =========================================================================
    // QUICK SORT – O(n log n) average-case time complexity
    //
    // Quick Sort is a divide-and-conquer algorithm. It selects a "pivot"
    // element and partitions the array so that all elements less than or equal
    // to the pivot come before it and all greater elements come after it. The
    // two sub-arrays are then sorted recursively. On average each level of
    // recursion processes O(n) elements over O(log n) levels, giving O(n log n)
    // overall. The sort is done in-place (O(log n) stack space).
    // =========================================================================

    /**
     * Public entry point for Quick Sort.
     * Sorts {@code arr} in ascending order using the in-place Quick Sort
     * algorithm. Time complexity: O(n log n) average, O(n²) worst case.
     *
     * @param arr the integer array to sort (modified in place)
     */
    public void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return; // nothing to sort
        }
        quickSortRecursive(arr, 0, arr.length - 1);
    }

    /**
     * Recursive helper that sorts the sub-array {@code arr[low..high]}.
     * Partitions around a pivot, then recursively sorts both halves.
     *
     * @param arr  the array being sorted
     * @param low  inclusive start index of the sub-array
     * @param high inclusive end index of the sub-array
     */
    private void quickSortRecursive(int[] arr, int low, int high) {
        if (low < high) {
            // Partition and get the pivot's final sorted position
            int pivotIndex = partition(arr, low, high);
            // Recursively sort elements before and after the pivot
            quickSortRecursive(arr, low, pivotIndex - 1);
            quickSortRecursive(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Partitions {@code arr[low..high]} using the last element as the pivot.
     * Elements ≤ pivot are moved to the left; elements > pivot to the right.
     *
     * @param arr  the array being partitioned
     * @param low  start index of the partition range
     * @param high end index (pivot element is at arr[high])
     * @return the final sorted index of the pivot
     */
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // last element chosen as pivot
        int i = low - 1; // boundary of the "≤ pivot" region

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                // Swap arr[i] and arr[j] to grow the left region
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Place pivot immediately after the left region
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1; // pivot's final index
    }

    // =========================================================================
    // BINARY SEARCH – O(log n) time complexity
    //
    // Binary Search requires the input array to be sorted. It repeatedly halves
    // the search space by comparing the target to the middle element. Because
    // each comparison eliminates half the remaining elements, the algorithm
    // finds (or rules out) any value in at most O(log n) comparisons.
    // =========================================================================

    /**
     * Searches for {@code target} in a sorted array using Binary Search.
     * Precondition: {@code sortedArr} must be sorted in ascending order.
     * Time complexity: O(log n).
     *
     * @param sortedArr the sorted integer array to search
     * @param target    the value to locate
     * @return the index of {@code target}, or -1 if not present
     */
    public int binarySearch(int[] sortedArr, int target) {
        int low = 0;
        int high = sortedArr.length - 1;

        while (low <= high) {
            // Compute midpoint safely to avoid integer overflow
            int mid = low + (high - low) / 2;

            if (sortedArr[mid] == target) {
                return mid; // found
            } else if (sortedArr[mid] < target) {
                low = mid + 1; // target is in the right half
            } else {
                high = mid - 1; // target is in the left half
            }
        }

        return -1; // target not found in the array
    }

    // =========================================================================
    // BENCHMARKING HELPERS
    // =========================================================================

    /**
     * Generates a random integer array of the given size.
     * Values are drawn from [0, size * 10) so the value range scales with size.
     *
     * @param size number of elements
     * @return a new randomly populated int[]
     */
    private int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size * 10);
        }
        return arr;
    }

    /**
     * Copies {@code arr}, sorts the copy with Quick Sort, and returns elapsed
     * nanoseconds. The original array is not modified.
     *
     * @param arr source array (unsorted)
     * @return Quick Sort execution time in nanoseconds
     */
    private long measureQuickSort(int[] arr) {
        // Work on a copy so the original data is preserved for other measurements
        int[] copy = new int[arr.length];
        System.arraycopy(arr, 0, copy, 0, arr.length);

        long start = System.nanoTime();
        quickSort(copy);
        long end = System.nanoTime();

        return end - start; // elapsed time in nanoseconds
    }

    /**
     * Searches for a random element from {@code sortedArr} using Binary Search
     * and returns elapsed nanoseconds.
     *
     * The target is one of the values actually present in the array (to
     * exercise the typical successful-search path).
     *
     * @param sortedArr a sorted integer array
     * @return Binary Search execution time in nanoseconds
     */
    private long measureBinarySearch(int[] sortedArr) {
        // Pick a random element that is guaranteed to be in the array
        Random random = new Random();
        int target = sortedArr[random.nextInt(sortedArr.length)];

        long start = System.nanoTime();
        binarySearch(sortedArr, target);
        long end = System.nanoTime();

        return end - start; // elapsed time in nanoseconds
    }

    // =========================================================================
    // MAIN – triggers the complete benchmark automatically
    // =========================================================================

    /**
     * Entry point. Automatically runs benchmarks for dataset sizes 100, 500,
     * and 1000, then prints a formatted comparison table to the console.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        PerformanceAnalyzer analyzer = new PerformanceAnalyzer();

        // Dataset sizes to benchmark as required by Module 3
        int[] sizes = { 100, 500, 1000 };

        // Arrays to store timing results for table display
        long[] quickSortTimes = new long[sizes.length];
        long[] binarySearchTimes = new long[sizes.length];

        System.out.println("============================================================");
        System.out.println("         Algorithm Performance Analyzer");
        System.out.println("============================================================");
        System.out.println("  Running benchmarks for dataset sizes: 100, 500, 1000 ...");
        System.out.println();

        // ------------------------------------------------------------------
        // Run benchmarks for each dataset size
        // ------------------------------------------------------------------
        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];

            // 1. Generate a random (unsorted) array for Quick Sort
            int[] randomArray = analyzer.generateRandomArray(size);

            // 2. Measure Quick Sort time – O(n log n)
            quickSortTimes[i] = analyzer.measureQuickSort(randomArray);

            // 3. Sort the array so Binary Search can run on it
            // (Binary Search requires a sorted array – O(log n))
            analyzer.quickSort(randomArray); // array is now sorted in place

            // 4. Measure Binary Search time – O(log n)
            binarySearchTimes[i] = analyzer.measureBinarySearch(randomArray);
        }

        // ------------------------------------------------------------------
        // Print the comparison table using System.out.printf
        //
        // Columns:
        // Dataset Size – number of elements in the test array
        // Quick Sort Time(ns) – measured in nanoseconds via System.nanoTime()
        // Binary Search Time(ns) – measured in nanoseconds via System.nanoTime()
        //
        // Complexity annotations:
        // Quick Sort → O(n log n) average-case time complexity
        // Binary Search → O(log n) time complexity
        // ------------------------------------------------------------------

        System.out.println("============================================================");
        System.out.println("             PERFORMANCE COMPARISON TABLE");
        System.out.println("============================================================");
        System.out.printf("| %-14s | %22s | %24s |%n",
                "Dataset Size",
                "Quick Sort Time (ns)", // Quick Sort – O(n log n)
                "Binary Search Time (ns)"); // Binary Search – O(log n)
        System.out.println("|----------------|------------------------|--------------------------|");

        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("| %-14d | %,22d | %,24d |%n",
                    sizes[i],
                    quickSortTimes[i],
                    binarySearchTimes[i]);
        }

        System.out.println("============================================================");
        System.out.println();
        System.out.println("  Complexity analysis:");
        System.out.println("    • Quick Sort  : O(n log n) average-case time complexity");
        System.out.println("    • Binary Search: O(log n) time complexity");
        System.out.println();
        System.out.println("  Note: times measured with System.nanoTime() for high precision.");
        System.out.println("============================================================");
    }
}

import java.util.Random;

public class PerformanceAnalyzer {

    public void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSortRecursive(arr, 0, arr.length - 1);
    }

    private void quickSortRecursive(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSortRecursive(arr, low, pivotIndex - 1);
            quickSortRecursive(arr, pivotIndex + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    public int binarySearch(int[] sortedArr, int target) {
        int low = 0;
        int high = sortedArr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (sortedArr[mid] == target) {
                return mid;
            } else if (sortedArr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    private int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size * 10);
        }
        return arr;
    }

    private long measureQuickSort(int[] arr) {
        int[] copy = new int[arr.length];
        System.arraycopy(arr, 0, copy, 0, arr.length);

        long start = System.nanoTime();
        quickSort(copy);
        long end = System.nanoTime();

        return end - start;
    }

    private long measureBinarySearch(int[] sortedArr) {
        Random random = new Random();
        int target = sortedArr[random.nextInt(sortedArr.length)];

        long start = System.nanoTime();
        binarySearch(sortedArr, target);
        long end = System.nanoTime();

        return end - start;
    }

    public static void main(String[] args) {

        PerformanceAnalyzer analyzer = new PerformanceAnalyzer();

        int[] sizes = { 100, 500, 1000 };

        long[] quickSortTimes = new long[sizes.length];
        long[] binarySearchTimes = new long[sizes.length];

        System.out.println("============================================================");
        System.out.println("         Algorithm Performance Analyzer");
        System.out.println("============================================================");
        System.out.println("  Running benchmarks for dataset sizes: 100, 500, 1000 ...");
        System.out.println();

        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];

            int[] randomArray = analyzer.generateRandomArray(size);

            quickSortTimes[i] = analyzer.measureQuickSort(randomArray);

            analyzer.quickSort(randomArray);

            binarySearchTimes[i] = analyzer.measureBinarySearch(randomArray);
        }

        System.out.println("============================================================");
        System.out.println("             PERFORMANCE COMPARISON TABLE");
        System.out.println("============================================================");
        System.out.printf("| %-14s | %22s | %24s |%n",
                "Dataset Size",
                "Quick Sort Time (ns)",
                "Binary Search Time (ns)");
        System.out.println("|----------------|------------------------|-----------------------------|");

        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("| %-14d | %,22d | %,24d |%n",
                    sizes[i],
                    quickSortTimes[i],
                    binarySearchTimes[i]);
        }

        System.out.println("============================================================");
        System.out.println();
        System.out.println("  Complexity analysis:");
        System.out.println("    * Quick Sort  : O(n log n) average-case time complexity");
        System.out.println("    * Binary Search: O(log n) time complexity");
        System.out.println();
        System.out.println("  Note: times measured with System.nanoTime() for high precision.");
        System.out.println("============================================================");
    }
}

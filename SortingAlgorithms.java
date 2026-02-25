import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SortingAlgorithms {

    public void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    public void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        mergeSortRecursive(arr, 0, arr.length - 1);
    }

    private void mergeSortRecursive(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortRecursive(arr, left, mid);
            mergeSortRecursive(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int sizeLeft = mid - left + 1;
        int sizeRight = right - mid;

        int[] leftArr = new int[sizeLeft];
        int[] rightArr = new int[sizeRight];

        for (int i = 0; i < sizeLeft; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int j = 0; j < sizeRight; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < sizeLeft && j < sizeRight) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < sizeLeft) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < sizeRight) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }

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

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        SortingAlgorithms sorter = new SortingAlgorithms();

        System.out.println("====================================================");
        System.out.println("       DATA SORTER - Algorithm Benchmark");
        System.out.println("====================================================");
        System.out.println();

        int[] originalData = null;

        while (originalData == null) {
            System.out.println("How would you like to provide the dataset?");
            System.out.println("  [1] Enter numbers manually");
            System.out.println("  [2] Generate a random dataset");
            System.out.print("Your choice (1 or 2): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Error: Please enter 1 or 2.\n");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    int count = 0;
                    while (count <= 0) {
                        System.out.print("How many numbers would you like to enter? ");
                        if (!scanner.hasNextInt()) {
                            System.out.println("Error: Please enter a valid positive number.");
                            scanner.next();
                            continue;
                        }
                        count = scanner.nextInt();
                        scanner.nextLine();
                        if (count <= 0) {
                            System.out.println("Error: Size must be greater than 0.");
                        }
                    }

                    originalData = new int[count];
                    System.out.println("Enter " + count + " integer(s), one per line:");

                    for (int idx = 0; idx < count; idx++) {
                        while (true) {
                            System.out.print("  Number " + (idx + 1) + ": ");
                            if (scanner.hasNextInt()) {
                                originalData[idx] = scanner.nextInt();
                                scanner.nextLine();
                                break;
                            } else {
                                System.out.println(
                                        "  Error: '" + scanner.next() + "' is not a valid integer. Try again.");
                            }
                        }
                    }
                    System.out.println("Successfully read " + count + " number(s).\n");
                    break;

                case 2:
                    int size = 0;
                    while (size <= 0) {
                        System.out.println("Choose a dataset size:");
                        System.out.println("  [1]  100 elements");
                        System.out.println("  [2]  500 elements");
                        System.out.println("  [3] 1000 elements");
                        System.out.print("Your choice (1, 2, or 3): ");

                        if (!scanner.hasNextInt()) {
                            System.out.println("Error: Please enter 1, 2, or 3.\n");
                            scanner.next();
                            continue;
                        }

                        int sizeChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (sizeChoice) {
                            case 1:
                                size = 100;
                                break;
                            case 2:
                                size = 500;
                                break;
                            case 3:
                                size = 1000;
                                break;
                            default:
                                System.out.println("Error: Invalid choice. Please enter 1, 2, or 3.\n");
                                break;
                        }
                    }

                    Random random = new Random();
                    originalData = new int[size];
                    for (int idx = 0; idx < size; idx++) {
                        originalData[idx] = random.nextInt(10000);
                    }
                    System.out.println("Generated a random dataset of " + size + " number(s).\n");
                    break;

                default:
                    System.out.println("Error: Invalid choice. Please enter 1 or 2.\n");
                    break;
            }
        }

        System.out.println("------------ Original Dataset ------------");
        System.out.println(Arrays.toString(originalData));
        System.out.println("Dataset size: " + originalData.length);

        int[] bubbleData = Arrays.copyOf(originalData, originalData.length);
        int[] mergeData = Arrays.copyOf(originalData, originalData.length);
        int[] quickData = Arrays.copyOf(originalData, originalData.length);

        long bubbleStart = System.nanoTime();
        sorter.bubbleSort(bubbleData);
        long bubbleTime = System.nanoTime() - bubbleStart;

        long mergeStart = System.nanoTime();
        sorter.mergeSort(mergeData);
        long mergeTime = System.nanoTime() - mergeStart;

        long quickStart = System.nanoTime();
        sorter.quickSort(quickData);
        long quickTime = System.nanoTime() - quickStart;

        System.out.println("\n------------ Sorted Output ------------");
        System.out.println("Bubble Sort : " + Arrays.toString(bubbleData));
        System.out.println("Merge Sort  : " + Arrays.toString(mergeData));
        System.out.println("Quick Sort  : " + Arrays.toString(quickData));

        System.out.println();
        System.out.println("====================================================");
        System.out.println("           PERFORMANCE COMPARISON TABLE");
        System.out.println("====================================================");
        System.out.printf("| %-20s | %22s |%n", "Algorithm Name", "Execution Time (ns)");
        System.out.println("|----------------------|------------------------|");
        System.out.printf("| %-20s | %,22d |%n", "Bubble Sort", bubbleTime);
        System.out.printf("| %-20s | %,22d |%n", "Merge Sort", mergeTime);
        System.out.printf("| %-20s | %,22d |%n", "Quick Sort", quickTime);
        System.out.println("====================================================");

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

        System.out.printf("%n>> Fastest: %s (%,d ns)%n", fastest, fastestTime);
        System.out.println("\nThank you for using the Data Sorter!");

        scanner.close();
    }
}

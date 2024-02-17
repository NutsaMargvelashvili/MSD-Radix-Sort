import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortingArray {
    static int findMax(int[] arr)
    {
        int maxItem = arr[0];
        for (int i = 1; i < arr.length; i++)
            if (arr[i] > maxItem)
                maxItem = arr[i];
        return maxItem;
    }

    static void countingSort(int[] arr, int[] arrToSort, int place) {

        int size = arrToSort.length;
        int[] output = new int[size + 1];
        int maxItem = findMax(arr);
        int initialIndex = findIndex(arr, arrToSort[0]); // the index where arrToSort is located in arr

        int[] count = new int[maxItem + 1];
        for (int i = 0; i < maxItem; ++i)
            count[i] = 0;

        // Determine count of elements
        for (int i = 0; i < size; i++)
            count[(arrToSort[i] / place) % 10]++;

        // Determine cummulative count
        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Place the elements in the correct place
        for (int i = size - 1; i >= 0; i--) {
            output[count[(arrToSort[i] / place) % 10] - 1] = arrToSort[i];
            count[(arrToSort[i] / place) % 10]--;
        }
        
     
        for (int i = 0; i < size; i++)
            arr[initialIndex + i] = output[i];
        
        if(place >= 10){
            // Map to store numbers based on their leading digits
             Map<Integer, List<Integer>> groupedNumbers = new HashMap<>();
    
             // Group numbers based on their leading digits
            for (int i = 0; i < size; i++) {
                int leadingDigit = output[i] / place;
                groupedNumbers.putIfAbsent(leadingDigit, new ArrayList<>());
                groupedNumbers.get(leadingDigit).add(output[i]);
            }
            
            // Convert grouped numbers to arrays
            int[][] groupedArrays = new int[groupedNumbers.size()][];
            int index = 0;
            for (List<Integer> group : groupedNumbers.values()) {
                groupedArrays[index++] = group.stream().mapToInt(Integer::intValue).toArray();
            }
        
            // Display the grouped numbers
            for (int[] group : groupedArrays) {
                if(group.length > 1){
                    countingSort(arr, group, place / 10);
                }
            }
        }
             
    }
    
    // Get number of place
    static int numPlace(int number) {
        int place = 1;
        
        while(number / place >= 10){
            place *= 10;
        }
        return place;
    }

    // Main function to implement radix sort
    static void radixSort(int[] arr) {
        int max = findMax(arr);
        int maxPlace = numPlace(max);

        countingSort(arr, arr, maxPlace);
    }
    
    public static int findIndex(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // Return the index if the element is found
            }
        }
        return -1; // Return -1 if the element is not found
    }

    static void showArray(int[] arr)
    {
        for (int value : arr) System.out.print(value + " ");
        System.out.println();
    }

    public static void main(String []args) {
        int[] libraryNum = {1241,2315,456,123,756,476,285,998,379,108};
        System.out.println("Initial array:");
        showArray(libraryNum);
        radixSort(libraryNum);
        System.out.println("Sorted array:");
        showArray(libraryNum);
    }
}

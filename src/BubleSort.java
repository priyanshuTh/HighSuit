import java.util.Arrays; // Import: for printing arrays as strings

// Class definition: contains a bubble sort and tracks comparisons
public class BubleSort {
    public static void bubleSort(int[] myArray) {
        int firstPos = 0;
        int lastPos = myArray.length - 1;
        int lastSwapPos, temp, numComparisons = 0, numSwaps = 0;
        
        while (firstPos < lastPos) {
            lastSwapPos = firstPos;
            
            for (int i = firstPos; i <= lastPos - 1; i++) {
                numComparisons++;
                if (myArray[i] > myArray[i + 1]) {
                    numSwaps++;
                    
                    temp = myArray[i + 1];
                    myArray[i + 1] = myArray[i];
                    myArray[i] = temp;
                    
                    lastSwapPos = i;
                }
            }
            lastPos = lastSwapPos;
        }
        
        System.out.printf("I made %d comparisons and %d swaps%n", numComparisons, numSwaps);
    }
    
    public static void main(String[] args) {
        
        int[] arr1 = { 5, 10, 9, 3, 54, 23, 1, 6, 32, 8 };
        System.out.println("\n" + Arrays.toString(arr1));
        bubleSort(arr1);
        System.out.println(Arrays.toString(arr1));
        
        int[] arr2 = {1, 3, 5, 6, 8, 9, 10, 23, 32, 54 };
        System.out.println("\n" + Arrays.toString(arr2));
        bubleSort(arr2);
        System.out.println(Arrays.toString(arr2));
        
        int[] arr3 = {54, 32, 23, 10, 9, 8, 6, 5, 3, 1 };
        System.out.println("\n" + Arrays.toString(arr3));
        bubleSort(arr3);
        System.out.println(Arrays.toString(arr3));  
    }
}
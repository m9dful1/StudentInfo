import java.util.ArrayList;
import java.util.Comparator;

/**
 * A utility class for sorting an ArrayList of Students using selection sort.
 */
public class SelectionSort {
    /**
     * Sorts the given list of students using the specified comparator.
     * @param list The list of students to sort.
     * @param comparator The comparator to use for sorting.
     */
    public static void sort(ArrayList<Student> list, Comparator<Student> comparator) { // Selection sort algorithm
        for (int i = 0; i < list.size() - 1; i++) { // Iterate over the list
            int minIndex = i; // Assume the minimum element is at index i
            for (int j = i + 1; j < list.size(); j++) { // Find the index of the minimum element in the unsorted part of the list
                if (comparator.compare(list.get(j), list.get(minIndex)) < 0) { // Use the comparator to compare list[j] and list[minIndex]
                    minIndex = j; // Update minIndex if list[j] < list[minIndex]
                }
            }
            Student temp = list.get(minIndex); // Swap list[i] with list[minIndex]
            list.set(minIndex, list.get(i)); // list[minIndex] = list[i]
            list.set(i, temp); // list[i] = temp
        }
    }
}

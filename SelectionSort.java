import java.util.ArrayList;
import java.util.Comparator;

public class SelectionSort {
    public static void sort(ArrayList<Student> list, Comparator<Student> comparator) {
        if (list == null) {
            throw new IllegalArgumentException("Please enter a valid list of students");
        }

        for (int i = 0; i < list.size(); i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (comparator.compare(list.get(j), list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            Student temp = list.get(i);
            list.set(i, list.get(minIndex));
            list.set(minIndex, temp);
        }
    }
}

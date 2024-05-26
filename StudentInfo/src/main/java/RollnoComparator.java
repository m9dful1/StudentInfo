import java.util.Comparator;

/**
 * Comparator class to compare students by their roll numbers.
 */
public class RollnoComparator implements Comparator<Student> { // Implement the Comparator interface
    @Override
    public int compare(Student o1, Student o2) { // Implement the compare method
        return Integer.compare(o1.getRollno(), o2.getRollno()); // Compare the roll numbers of the students
    }
}

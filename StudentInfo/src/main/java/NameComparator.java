import java.util.Comparator;

/**
 * Comparator class to compare students by their names.
 */
public class NameComparator implements Comparator<Student> { // Implement the Comparator interface
    @Override 
    public int compare(Student o1, Student o2) { // Implement the compare method
        return o1.getName().compareTo(o2.getName()); // Compare the names of the students
    }
}

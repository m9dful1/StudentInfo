import java.util.Comparator;

public class NameComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        if (o1 == null || o2 == null || o1.getName() == null || o2.getName() == null) {
            throw new IllegalArgumentException("Please make sure all students have names");
        }
        return o1.getName().compareTo(o2.getName());
    }
}

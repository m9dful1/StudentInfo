import java.util.Comparator;

public class RollnoComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        if (o1 == null || o2 == null) {
            throw new IllegalArgumentException("Please make sure all student entries are valid");
        }
        return Integer.compare(o1.getRollno(), o2.getRollno());
    }
}

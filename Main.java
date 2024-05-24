import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(10, "John", "123 Main St"));
        students.add(new Student(5, "Alice", "456 Elm St"));
        students.add(new Student(8, "Bob", "789 Oak St"));
        students.add(new Student(3, "Charlie", "321 Maple St"));
        students.add(new Student(6, "David", "654 Pine St"));
        students.add(new Student(9, "Eve", "987 Cedar St"));
        students.add(new Student(1, "Frank", "111 Spruce St"));
        students.add(new Student(4, "Grace", "222 Birch St"));
        students.add(new Student(7, "Henry", "333 Aspen St"));
        students.add(new Student(2, "Ivy", "444 Walnut St"));

        System.out.println("Original List:");
        printStudents(students);

        SelectionSort.sort(students, new NameComparator());
        System.out.println("\nSorted by Name:");
        printStudents(students);

        SelectionSort.sort(students, new RollnoComparator());
        System.out.println("\nSorted by Roll Number:");
        printStudents(students);
    }

    public static void printStudents(ArrayList<Student> list) {
        for (Student student : list) {
            System.out.println(student);
        }
    }
}

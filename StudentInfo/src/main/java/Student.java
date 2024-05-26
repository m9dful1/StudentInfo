/**
 * Represents a student with a roll number, name, and address.
 */
public class Student {
    private int rollno;
    private String name;
    private String address;

    /**
     * Constructor to initialize a student with the given roll number, name, and address.
     * @param rollno The roll number of the student.
     * @param name The name of the student.
     * @param address The address of the student.
     */
    public Student(int rollno, String name, String address) {
        this.rollno = rollno;
        this.name = name;
        this.address = address;
    }

    // Getters and setters for the student's attributes

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollno=" + rollno +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

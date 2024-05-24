public class Student {
    private int rollno;
    private String name;
    private String address;

    public Student(int rollno, String name, String address) {
        if (rollno <= 0) {
            throw new IllegalArgumentException("Please enter a positive roll number");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Please enter a name");
        }
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Please enter an address");
        }

        this.rollno = rollno;
        this.name = name;
        this.address = address;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        if (rollno <= 0) {
            throw new IllegalArgumentException("Roll number must be positive");
        }
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Please enter a name");
        }
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Please enter an address");
        }
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

import java.util.*;

// 1. Student class with Roll Number, Name, and Percentage
class Student {
    int rollNo;
    String name;
    double percentage;

    public Student(int rollNo, String name, double percentage) {
        this.rollNo = rollNo;
        this.name = name;
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return String.format("%-7d %-8s %.1f", rollNo, name, percentage);
    }
}

public class StudentRecord {

    public static void main(String[] args) {
        // 2 & 3. Create ArrayList and HashMap
        List<Student> studentList = new ArrayList<>();
        Map<Integer, Student> studentMap = new HashMap<>();

        // 4. Add student records
        Student s1 = new Student(101, "Rahul", 88.5);
        Student s2 = new Student(102, "Sneha", 91.2);
        Student s3 = new Student(103, "Kiran", 84.8);

        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);

        studentMap.put(s1.rollNo, s1);
        studentMap.put(s2.rollNo, s2);
        studentMap.put(s3.rollNo, s3);

        // 5. Display all records in ArrayList
        System.out.println("Student Records (ArrayList)\n");
        System.out.println("---------------------------------");
        System.out.printf("%-7s %-8s %-10s%n", "Roll", "Name", "Percentage");
        System.out.println("---------------------------------");
        for (Student s : studentList) {
            System.out.println(s);
        }
        System.out.println();

        // 6. Search for student with Roll No 102 from HashMap
        int searchRoll = 102;
        System.out.println("Searching for Roll No : " + searchRoll + "\n");
        if (studentMap.containsKey(searchRoll)) {
            Student found = studentMap.get(searchRoll);
            System.out.println("Record Found\n");
            System.out.println("Roll No    : " + found.rollNo);
            System.out.println("Name       : " + found.name);
            System.out.println("Percentage : " + found.percentage);
        } else {
            System.out.println("Record Not Found");
        }
        System.out.println();

        // 7. Remove first student from ArrayList
        System.out.println("After Removing First Student\n");
        if (!studentList.isEmpty()) {
            studentList.remove(0);
        }

        // 8. Display updated ArrayList records
        System.out.println("---------------------------------");
        System.out.printf("%-7s %-8s %-10s%n", "Roll", "Name", "Percentage");
        System.out.println("---------------------------------");
        for (Student s : studentList) {
            System.out.println(s);
        }
        System.out.println();

        // 9. Display all entries stored in the HashMap
        System.out.println("Student Records (HashMap)\n");
        System.out.println("---------------------------------");
        for (Map.Entry<Integer, Student> entry : studentMap.entrySet()) {
            Student s = entry.getValue();
            System.out.println(s.rollNo + " -> " + s.name + " (" + s.percentage + "%)");
        }
    }
}

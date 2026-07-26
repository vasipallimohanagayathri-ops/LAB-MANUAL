import java.util.*;
import java.util.stream.Collectors;

// 1 & 3. Employee class with id, name, department, and salary
class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return id + " " + name + " " + department + " " + salary;
    }
}

public class EmployeeAnalytics {

    public static void main(String[] args) {
        // 4. Create a List of Employee objects
        List<Employee> employees = Arrays.asList(
            new Employee(101, "Rahul", "CSE", 55000.0),
            new Employee(102, "Sneha", "ECE", 62000.0),
            new Employee(103, "Kiran", "CSE", 48000.0),
            new Employee(104, "Divya", "MECH", 51000.0),
            new Employee(105, "Arjun", "ECE", 70000.0)
        );

        // 5. Display All Employees using forEach()
        System.out.println("---- All Employees ----");
        employees.forEach(emp -> System.out.println(emp));

        // 6. Filter salary above 50000, sort high to low
        System.out.println("---- Salary Above 50000 (High to Low) ----");
        employees.stream()
            .filter(e -> e.getSalary() > 50000)
            .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
            .forEach(e -> System.out.println(e.getName() + " -> " + e.getSalary()));

        // 7. Extract Employee Names using map()
        System.out.println("---- Employee Names ----");
        List<String> employeeNames = employees.stream()
            .map(Employee::getName)
            .collect(Collectors.toList());
        System.out.println(employeeNames);

        // 8. Group Employee names by Department
        System.out.println("---- Employees Grouped by Department ----");
        Map<String, List<String>> empByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.mapping(Employee::getName, Collectors.toList())
            ));
        empByDept.forEach((dept, names) -> System.out.println(dept + " : " + names));

        // 9. Average Salary per Department
        System.out.println("---- Average Salary per Department ----");
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
        avgSalaryByDept.forEach((dept, avgSal) -> 
            System.out.printf(Locale.US, "%s : %.2f%n", dept, avgSal)
        );

        // 10. Compute Total Salary using reduce()
        double totalSalary = employees.stream()
            .map(Employee::getSalary)
            .reduce(0.0, Double::sum);
        System.out.printf(Locale.US, "Total Salary Paid : %.2f%n", totalSalary);

        // 11. Count CSE Employees
        long cseCount = employees.stream()
            .filter(e -> e.getDepartment().equalsIgnoreCase("CSE"))
            .count();
        System.out.println("Number of CSE Employees : " + cseCount);

        // 11. Find Highest Paid Employee using max() and Optional
        Optional<Employee> highestPaid = employees.stream()
            .max(Comparator.comparingDouble(Employee::getSalary));

        highestPaid.ifPresent(e -> 
            System.out.println("Highest Paid : " + e.getName() + " (" + e.getSalary() + ")")
        );
    }
}

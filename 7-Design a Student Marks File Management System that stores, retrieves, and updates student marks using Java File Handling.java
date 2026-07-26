import java.io.*;
import java.util.Scanner;

public class StudentFile {
    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Details of 3 Students\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Student " + i);
                System.out.print("Roll No : ");
                String rollNo = sc.nextLine().trim();

                System.out.print("Name : ");
                String name = sc.nextLine().trim();

                System.out.print("Marks : ");
                String marks = sc.nextLine().trim();
                writer.write(rollNo + "," + name + "," + marks);
                writer.newLine();
                System.out.println();
            }
            writer.flush();
            System.out.println("Student records saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        System.out.println("----- Student Records -----");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        System.out.print("\nEnter Roll Number to Search : ");
        String searchRoll = sc.nextLine().trim();
        System.out.println();

        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equals(searchRoll)) {
                    System.out.println("Student Found\n");
                    System.out.println("Roll No : " + parts[0].trim());
                    System.out.println("Name : " + parts[1].trim());
                    System.out.println("Marks : " + parts[2].trim());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Student Not Found");
            }
        } catch (IOException e) {
            System.out.println("Error searching file: " + e.getMessage());
        }
        sc.close();
    }
}

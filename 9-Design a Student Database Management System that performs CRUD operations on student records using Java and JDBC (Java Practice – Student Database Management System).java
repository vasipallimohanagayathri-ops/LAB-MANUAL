/*
 ============================================================================
 SQL DATABASE SETUP (Run these commands in MySQL before executing Java code):
 ============================================================================
 CREATE DATABASE college;
 USE college;

 CREATE TABLE student (
     rollno INT PRIMARY KEY,
     name VARCHAR(30),
     department VARCHAR(20),
     marks INT
 );
 ============================================================================
*/

import java.sql.*;

public class StudentJDBC {
    // Note: Default MySQL port is typically 3306. Change to 3606 if configured.
    private static final String URL = "jdbc:mysql://localhost:3306/college?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // 1. Insert Records
            String insertSQL = "INSERT INTO student (rollno, name, department, marks) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmtInsert = conn.prepareStatement(insertSQL)) {
                pstmtInsert.setInt(1, 101);
                pstmtInsert.setString(2, "Rahul");
                pstmtInsert.setString(3, "CSE");
                pstmtInsert.setInt(4, 87);
                pstmtInsert.executeUpdate();

                pstmtInsert.setInt(1, 102);
                pstmtInsert.setString(2, "Sneha");
                pstmtInsert.setString(3, "ISE");
                pstmtInsert.setInt(4, 91);
                pstmtInsert.executeUpdate();

                System.out.println("Records Inserted Successfully.\n");
            }

            // 2. Update Record
            String updateSQL = "UPDATE student SET marks = ? WHERE rollno = ?";
            try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateSQL)) {
                pstmtUpdate.setInt(1, 95);
                pstmtUpdate.setInt(2, 101);
                pstmtUpdate.executeUpdate();

                System.out.println("Record Updated Successfully.\n");
            }

            // 3. Search Record
            String searchSQL = "SELECT * FROM student WHERE rollno = ?";
            try (PreparedStatement pstmtSearch = conn.prepareStatement(searchSQL)) {
                pstmtSearch.setInt(1, 101);
                ResultSet rsSearch = pstmtSearch.executeQuery();

                System.out.println("Student Details\n");
                if (rsSearch.next()) {
                    System.out.println("Roll No    : " + rsSearch.getInt("rollno"));
                    System.out.println("Name       : " + rsSearch.getString("name"));
                    System.out.println("Department : " + rsSearch.getString("department"));
                    System.out.println("Marks      : " + rsSearch.getInt("marks"));
                }
                System.out.println();
            }

            // 4. Display All Records
            String displaySQL = "SELECT * FROM student";
            try (PreparedStatement pstmtDisplay = conn.prepareStatement(displaySQL)) {
                ResultSet rsDisplay = pstmtDisplay.executeQuery();

                System.out.println("Student Records");
                System.out.println("------------------------------------------------");
                System.out.printf("%-7s %-8s %-15s %-5s%n", "Roll", "Name", "Department", "Marks");
                System.out.println("------------------------------------------------");

                while (rsDisplay.next()) {
                    System.out.printf("%-7d %-8s %-15s %-5d%n",
                            rsDisplay.getInt("rollno"),
                            rsDisplay.getString("name"),
                            rsDisplay.getString("department"),
                            rsDisplay.getInt("marks"));
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } finally {
            // Close Connection
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}

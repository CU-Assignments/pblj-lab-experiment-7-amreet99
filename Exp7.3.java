Setup MySQL Database
   Code:

CREATE DATABASE StudentDB;
USE StudentDB;
CREATE TABLE Student (
    StudentID INT PRIMARY KEY,
    Name VARCHAR(100),
    Department VARCHAR(50),
    Marks DOUBLE
);


Java MVC Structure
   Code:

    public class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;

    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getMarks() { return marks; }

    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setMarks(double marks) { this.marks = marks; }
}



Controller 
   Code:

   import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/StudentDB";
    private static final String USER = amrit_paudel;
    private static final String PASSWORD = amrit@14341;

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addStudent(Student student) {
        String sql = "INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setInt(1, student.getStudentID());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getDepartment());
            pstmt.setDouble(4, student.getMarks());
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(rs.getInt("StudentID"), rs.getString("Name"),
                        rs.getString("Department"), rs.getDouble("Marks")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void updateStudent(int studentID, String name, String department, double marks) {
        String sql = "UPDATE Student SET Name=?, Department=?, Marks=? WHERE StudentID=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setString(1, name);
            pstmt.setString(2, department);
            pstmt.setDouble(3, marks);
            pstmt.setInt(4, studentID);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int studentID) {
        String sql = "DELETE FROM Student WHERE StudentID=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            pstmt.setInt(1, studentID);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}







View 
Code:


 import java.util.List;
import java.util.Scanner;

public class StudentView {
    public static void displayStudents(List<Student> students) {
        System.out.println("\nStudentID | Name | Department | Marks");
        System.out.println("--------------------------------------");
        for (Student s : students) {
            System.out.println(s.getStudentID() + " | " + s.getName() + " | " + s.getDepartment() + " | " + s.getMarks());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        while (true) {
            System.out.println("\n1. Add Student\n2. View Students\n3. Update Student\n4. Delete Student\n5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = scanner.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = scanner.nextDouble();
                    dao.addStudent(new Student(id, name, dept, marks));
                    break;
                case 2:
                    List<Student> students = dao.getStudents();
                    displayStudents(students);
                    break;
                case 3:
                    System.out.print("Enter Student ID to update: ");
                    int updateID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New Department: ");
                    String newDept = scanner.nextLine();
                    System.out.print("Enter New Marks: ");
                    double newMarks = scanner.nextDouble();
                    dao.updateStudent(updateID, newName, newDept, newMarks);
                    break;
                case 4:
                    System.out.print("Enter Student ID to delete: ");
                    int deleteID = scanner.nextInt();
                    dao.deleteStudent(deleteID);
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}



Compile and Run the Program
javac Student.java StudentDAO.java StudentView.java
java StudentView





Setup MySQL Database :

Code:

CREATE DATABASE CompanyDB;
USE CompanyDB;

CREATE TABLE Employee (
    EmpID INT PRIMARY KEY,
    Name VARCHAR(100),
    Salary DECIMAL(10,2)
);

INSERT INTO Employee (EmpID, Name, Salary) VALUES
(1, 'Alice', 50000.00),
(2, 'Bob', 60000.00),
(3, 'Charlie', 55000.00);

  
Java Program to Connect to MySQL

   Code :
   
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/CompanyDB";
        String user = "your_username";
        String password = "your_password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
            System.out.println("EmpID | Name    | Salary");
            System.out.println("-------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("EmpID") + "  | " + 
                                   rs.getString("Name") + "  | " + 
                                   rs.getDouble("Salary"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

















   

5. **Verify Output**  
   - Ensure that employee records are displayed correctly from the database.

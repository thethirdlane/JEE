package ttl.larku.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ttl.larku.domain.Student;

public class JDBCDemo {

	public static void main(String[] args) {
		Statement statement = null;
		Connection connection = null;
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");

			connection = DriverManager.getConnection(
					"jdbc:derby://localhost:1527/LarkUDB", "larku", "larku");
			connection.setAutoCommit(false);
			

			statement = connection.createStatement();

			int result = statement.executeUpdate("Insert into SCHEDULEDCLASS_STUDENT values (2, 2)");

			connection.commit();
			System.out.println("Result is: " + result);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				statement.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

	}
	public static void mainToo(String[] args) {
		Statement statement = null;
		Connection connection = null;
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");

			connection = DriverManager.getConnection(
					"jdbc:derby://localhost:1527/LarkUDB", "larku", "larku");

			statement = connection
					.createStatement();

			ResultSet rs = statement.executeQuery("Select * from Student");

			List<Student> students = new ArrayList<Student>();
			while (rs.next()) {
				String name = rs.getString("name");
				String phoneNumber = rs.getString("phoneNumber");

				Student student = new Student();
				student.setName(name);
				student.setPhoneNumber(phoneNumber);
				students.add(student);
			}

			System.out.println(students);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				statement.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

	}
}

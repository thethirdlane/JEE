package ttl.larku.db;

import ttl.larku.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCDemo {

	public static void main(String[] args) {
		Statement statement = null;
		Connection connection = null;
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");

			connection = DriverManager.getConnection(
					"jdbc:derby://localhost:1527/LarkUDB", "larku", "larku");

			statement = connection.createStatement();

			PreparedStatement statement2 = connection.prepareStatement("select * from student where id = ?");
			statement2.setInt(1, 2);

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
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				statement.close();
				connection.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

	}
}

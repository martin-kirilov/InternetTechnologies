import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Pure SQL statements for creating, filling, updating and deleting data from the table.
public class SQLExample {
	
	public static void selectAll(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT * FROM table_people");
		while (rs.next()) {
			String name = rs.getString("name");
			int age = rs.getInt("age");
			String birthplace = rs.getString("birthplace");			
			System.out.println(name + " " + age + " " + birthplace);
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		Connection con = DriverManager.getConnection("jdbc:derby:wombat;create=true");
		Statement stmt = con.createStatement();
		stmt.execute("CREATE TABLE table_people (name VARCHAR(45), age INT, birthplace VARCHAR(30))");
		
		stmt.execute("INSERT INTO table_people VALUES ('Pesho', 18, 'Sofia')");
		stmt.execute("INSERT INTO table_people VALUES ('Ivan', 19, 'Pernik')");
		stmt.execute("INSERT INTO table_people VALUES ('Georgi', 17, 'Sofia')");
		
		stmt = con.createStatement();
		
		selectAll(stmt);
		
		stmt.execute("UPDATE table_people SET name='Ivan Manev' WHERE birthplace='Pernik'");
		stmt.execute("DELETE FROM table_people WHERE age<18");
		
		System.out.println("-----------------------");
	
		selectAll(stmt);
	}

}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Prepared statement example.
public class PreparedStmtExample {
	
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
		
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		stmt.execute("CREATE TABLE table_people (name VARCHAR(45), age INT, birthplace VARCHAR(30))");
		stmt.execute("INSERT INTO table_people VALUES ('Pesho', 18, 'Sofia')");
		stmt.execute("INSERT INTO table_people VALUES ('Ivan', 19, 'Pernik')");
		stmt.execute("INSERT INTO table_people VALUES ('Georgi', 17, 'Sofia')");
		
		selectAll(stmt);
		
		PreparedStatement updateName = con.prepareStatement("UPDATE table_people SET name = ? "
				+ "WHERE birthplace LIKE ?");
		
		updateName.setString(1, "Ivan Manev"); 
		updateName.setString(2, "Pernik"); 
		
		int res = updateName.executeUpdate();
		if(res == 1) {
			System.out.println("You succeeded in updating 1 row from your table, good job :)");
		}
		
		selectAll(stmt);
	}

}

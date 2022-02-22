package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private static Connection conn = null;

	public static Connection getConnection() {
		if (conn == null) {
			try {
				String driver = "com.mysql.cj.jdbc.Driver";
				String url = "jdbc:mysql://localhost:3306/shopmallmanageDB?useUnicode=true&characterEncoding=utf8";
				String user = "root";
				String pwd = "1234";
				Class.forName(driver);
				conn = DriverManager.getConnection(url, user, pwd);
				System.out.println("Connect");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
}

package com.uttara.project.lloyd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionEstablisher {

	public Connection connect() {
		String url = "jdbc:hsqldb:hsql://localhost/";
		String uid = "SA";
		String pwrd = "";
		String dvrName = "org.hsqldb.jdbcDriver";
		Connection con = null;
		try {
			Class.forName(dvrName);
			con = DriverManager.getConnection(url, uid, pwrd);
			System.out.println(con);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return con;

	}
	
//	public static void main(String[] args) {
//		Connection con = connect();
//		System.out.println(con);
//		
//	}

}

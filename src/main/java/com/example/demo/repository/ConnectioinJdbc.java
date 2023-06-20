package com.example.demo.repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;
@Repository
public class ConnectioinJdbc {
	public   Connection connect() throws SQLException {
	    try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Asdf", "Anemoi", "Anemoi@123");
	        System.out.println("Database Is Connected");
	        return con;
	    } catch (Exception e) {
	        System.out.println("Database Is Not Connected");
	        System.out.println(e);
	    }
	    return null;
	}

}

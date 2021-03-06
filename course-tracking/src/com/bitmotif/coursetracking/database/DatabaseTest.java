package com.bitmotif.coursetracking.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class DatabaseTest extends Database {

	@Test
	public void testWeCanGetAConnection() throws SQLException {
		Database database = new Database();
		
		Connection connection = database.getConnection();
		
		try {
			assertNotNull(connection);	
		}
		finally {
			connection.close();
		}
	}

}

package org.ml.pool;

import java.sql.Connection;

abstract class ConnectionPoolAbstract {

	public static final int MIN_NUMBER_OF_CONNECTIONS = 20;
	public static final int MAX_NUMBER_OF_CONNECTIONS = 50;
	public static final int CONNECTION_TTL = 1000 * 60 * 5;
	
	
	abstract public void init();
	
	abstract public Connection createConnection();
	
	abstract public Connection borrowConnection();
	
	abstract public void returnConnection(Connection con);
	
}

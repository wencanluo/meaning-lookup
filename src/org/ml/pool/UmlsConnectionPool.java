package org.ml.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import org.ml.util.Global;

public class UmlsConnectionPool extends ConnectionPoolAbstract{
	
	protected static Hashtable<Connection,Date> freeCons = new Hashtable<Connection,Date>();
	protected static Hashtable<Connection,Date> lockedCons = new Hashtable<Connection,Date>();
	
	
	static UmlsConnectionPool instance = null;
	
	private UmlsConnectionPool(){ init(); }
	
	public static UmlsConnectionPool getInstance(){
		if(instance == null) instance = new UmlsConnectionPool();
		return instance;
	}

	public Connection createConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			 con = DriverManager.getConnection(Global.umlsDBProps.getProperty("url"), Global.umlsDBProps);
		} catch (SQLException e) {
			System.err.println("Problem with establishing connection to umls mysql");
		} catch (ClassNotFoundException e) {
			System.err.println("Problem mysql driver");	
		}
		return con;
	}

	public void init() {
		Date now = Calendar.getInstance().getTime();
		for(int i = 0 ; i < MIN_NUMBER_OF_CONNECTIONS ; i++) freeCons.put(createConnection(), now);
	}
	
	public synchronized Connection borrowConnection(){
		Enumeration<Connection> e = freeCons.keys();
		Date now = Calendar.getInstance().getTime();
		Connection con = null;
		if (e.hasMoreElements()) {
			con = e.nextElement();
			if(now.getTime() - freeCons.get(con).getTime() > CONNECTION_TTL) {
				freeCons.remove(con);
				con = createConnection();
				lockedCons.put(con, now);
			}else{
				lockedCons.put(con, freeCons.get(con));
				freeCons.remove(con);
			}
			return con;		
		}else
		if(lockedCons.size() < MAX_NUMBER_OF_CONNECTIONS){
			con = createConnection();
			lockedCons.put(con, now);
			return con;
		}
		return null;
	}
	
	public void returnConnection(Connection con){
		freeCons.put(con , lockedCons.remove(con));
	}

}

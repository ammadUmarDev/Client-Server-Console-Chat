package DataBase;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class DataBase {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	static final String DB_URL = "jdbc:mysql://localhost/";

	/** The name of the MySQL account to use (or empty for anonymous) */
	private String userName = null;

	/** The password for the MySQL account (or empty for anonymous) */
	private String password = null;

	/** The name of the computer running MySQL */
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/**
	 * The name of the database we are testing with (this default is installed with
	 * MySQL)
	 */
	private String dbName =null;

	/** The name of the table we are testing with */
	private Set tables;

	public Connection conn = null;

	private Statement stmt;

	public DataBase()
	{
		this.dbName=null;
		this.userName=null;
		this.password=null;
	}
	public DataBase(String username,String password,String dbname)
	{
		this.userName=username;
		this.password=password;
		this.dbName=dbname;
		this.tables=new HashSet<String>();
		this.runconn();
	}
	public void dbcreation() {
		this.conn = null;
		this.stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, this.userName, this.password);
			// STEP 4: Execute a query
			System.out.println("Creating database...");
			stmt = conn.createStatement();
			String sql = "CREATE DATABASE IF NOT EXISTS " + this.dbName;
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully...");

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}

	public void droptable() throws SQLException {
		Iterator<String>j=this.tables.iterator();
		while(j.hasNext())
		{
			String input = "DROP TABLE " + j.next();
			this.executeUpdate(conn, input);
		}

	}

	public void dbentry() {
		Scanner sc = new Scanner(System.in);
		System.out.println("What is the name of the DB:");
		this.userName = sc.nextLine();
		System.out.println("What is the password of the DB:");
		this.password = sc.nextLine();
		sc.close();
	}

	public void MakeDB() {
		stmt = null;
		String sql = "CREATE DATABASE " + this.dbName;
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Data Base Already Exists");
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);
		System.out.println("trying to get connection!! ");
		conn = DriverManager.getConnection(
				"jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName, connectionProps);
		System.out.println(" Connection achieved!! ");
		return conn;
	}

	public boolean executeUpdate(Connection conn, String command) throws SQLException {
		Statement stmt = null;
		try {
			stmt = this.conn.createStatement();
			stmt.executeUpdate(command); // This will throw a SQLException if it fails
			return true;
		} finally {

			// This will run whether we throw an exception or not
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public boolean tablecheckfun(String name) throws SQLException {
		java.sql.DatabaseMetaData dbm = this.conn.getMetaData();
		ResultSet tables = dbm.getTables(null, null, name, null);
		if (tables.next() == true) {
			return true;
		}
		return false;
	}

	public void runconn() {
		this.dbcreation();
		this.conn = null;
		try {
			this.conn = this.getConnection();
			System.out.println("connection name is :: " + this.conn.getClass().getName());
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}

	}

	public void addtable(String name,String query)
	{
		try {
			boolean chck;
			chck=this.tablecheckfun(name);
			if(chck==true)
			{
				System.out.println("Table Already Exists");
			}
			else if(chck==false)
			{
				this.tables.add(name);
				this.executeUpdate(conn, query);
			}



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid Table making query");
			e.printStackTrace();
		}
	}

	public ResultSet tableinforetrieval(String tname) throws SQLException
	{
		ResultSet rs=null;
		this.stmt=null;
		String query="SELECT * FROM " + tname + ";";
		stmt=this.conn.createStatement();
		rs=stmt.executeQuery(query);
		return rs;
	}
}

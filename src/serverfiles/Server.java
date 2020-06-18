package serverfiles;

import java.util.*;

import DataBase.DataBase;

import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;

public class Server {
	ServerSocket socket;
	DataBase dbserver;
	int port;
	List<ClientThread> clients;
	List<Account> clientAccountsList;

	public Server() {
		this.socket = null;
		this.clients = null;
		this.port = -1;
	}

	public Server(int port, String dbPass) throws IOException, SQLException {

		this.port = port;
		try {
			// create server socket
			this.socket = new ServerSocket(this.port);

			// initializes threads array
			this.clients = new ArrayList<ClientThread>();

			// get all existing accounts from db
			this.clientAccountsList = new ArrayList<Account>();
			this.dbserver = new DataBase("root", "951062030", "SZATALK");

			// if Account table doesnt exist, create it. get all data from it
			this.dbserver.addtable("Account", "CREATE TABLE Account(username varchar(100), password varchar(100), "
					+ "fullname varchar(100), DOB varchar(100), phoneno varchar(100))");
			this.accountRetrievalFunction();

			// server goes busy waiting for clients
			this.run();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insertIntoAccount(Account obj) throws SQLException {
		String query = "INSERT INTO ACCOUNT" + " value(" + "'" + obj.username + "'," + "'" + obj.password + "'," + "'"
				+ obj.fullname + "'," + "'" + obj.DOB + "'," + "'" + obj.phoneno + "');";
		this.dbserver.executeUpdate(this.dbserver.conn, query);

	}

	public void accountRetrievalFunction() throws SQLException {
		ResultSet rs = null;
		Account temp = null;
		rs = this.dbserver.tableinforetrieval("Account");
		while (rs.next()) {
			temp = new Account();
			temp.username = rs.getString("username");
			temp.password = rs.getString("password");
			temp.fullname = rs.getString("fullname");
			temp.DOB = rs.getString("DOB");
			temp.phoneno = rs.getString("phoneno");
			this.clientAccountsList.add(temp);
		}

	}

	public void run() throws IOException {
		try {

			while (true) {
				System.out.println("Waiting For The Client");
				Socket acceptSocket = socket.accept();
				System.out.println("Client Accepted With port No:" + acceptSocket.getPort());
				ClientThread client = new ClientThread(this, acceptSocket);
				clients.add(client);
				client.start();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

	}

	public void display() {

		for (int i = 0; i < this.clientAccountsList.size(); i++) {
			this.clientAccountsList.get(i).display();
		}
	}

	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Enter password for db root: ");
		String dbPass = in.next();
		Server ibrar = new Server(5000, dbPass);
	}

}

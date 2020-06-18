package serverfiles;

//import Serialization.*;
import messages.*;
//import sharedStructures.clientlogin;
import sharedStructures.ReadThread;

import java.util.*;

import javax.sql.rowset.serial.SerialJavaObject;

import Serialization.SerializedObject;

import java.net.*;
import java.sql.SQLException;
import java.io.*;

public class ClientThread extends Thread {
	Socket acceptSocket;
	Server baseServer;
	ReadThread assistant;
	MessageFactory msgFac;
	String username;

	public ClientThread(Server bs, Socket os) throws IOException {
		this.acceptSocket = os;
		this.baseServer = bs;
		msgFac = new MessageFactory();
		assistant = new ReadThread(this.acceptSocket);
		assistant.start();
	}

	public void run() {
		while (true) {
			try {
				// while (assistant.getOut().size()<1);
				byte[] mType,msg;
				while (true) {
					try {
						mType = assistant.getOut().remove();
						break;
					} catch (NoSuchElementException e) {
						continue;
					}
				}
				String msgType = (new SerializedObject<String>()).fromByteStream(mType);
				System.out.println(msgType);
				while (true) {
					try {
						msg = assistant.getOut().remove();
						break;
					} catch (NoSuchElementException e) {
						continue;
					}
				}
				System.out.println("Message received");
				Message receivedMessage = msgFac.getMessage(msgType);
				receivedMessage.fromBinary(msg);
				if (msgType.equals("LoginRequest")) {

					if (this.clientLoginFunc((String) receivedMessage.getField("username"),
							(String) receivedMessage.getField("password"))) {
						this.acceptSocket.getOutputStream().write(mType);
						this.acceptSocket.getOutputStream().write((new String("Login Successfull")).getBytes());
					} else {
						this.baseServer.clients.remove(this);
						break;
					}

				} else {
					System.out.println("Invalid Message Type received");
					continue;
				}
			} catch (SocketException e) {
				this.baseServer.clients.remove(this);
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean clientLoginFunc(String username, String password) throws IOException {
		if (this.validateUserCredentials(username, password) == true) {
			this.username = username;
			return true;
		}
		return false;

	}

	public boolean validateUserCredentials(String username, String password) {
		for (int i = 0; i < this.baseServer.clientAccountsList.size(); i++) {
			if (this.baseServer.clientAccountsList.get(i).username.equals(username)
					&& this.baseServer.clientAccountsList.get(i).password.equals(password)) {
				return true;
			}
		}
		return false;
	}
}

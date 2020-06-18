package clientfiles;

import Serialization.*;
import messages.Message;
import messages.MessageFactory;
import sharedStructures.ReadThread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
	public Socket clientSocket;
	ReadThread assistant;
	MessageFactory msgFac;

	public Client() {
		this.clientSocket = null;
		msgFac = new MessageFactory();
		// this.out = null;
	}

	public Client(String hostname, int port) throws UnknownHostException, IOException {
		this.clientSocket = new Socket(hostname, port);// client takes a random
														// port on local machine
														// and connects with the
														// passed host on given
														// port

		// a listening thread for client
		assistant = new ReadThread(this.clientSocket);
		assistant.start();

		msgFac = new MessageFactory();
	}

	public boolean verifyCredentials(String username, String password) throws IOException {
		this.clientSocket.getOutputStream()
				.write((new SerializedObject<String>()).toByteStream(new String("LoginRequest")));

		String temp = new String("LoginRequest");
		Message lr = msgFac.getMessage(temp);
		lr.setField("username", username);
		lr.setField("password", password);
		this.clientSocket.getOutputStream().write(lr.toBinary());
		System.out.println("Message dispatched to Server");
		Message receiveMessage = msgFac.getMessage("LoginRequest");
		receiveMessage.fromBinary(lr.toBinary());
		while (assistant.getOut().size() <= 0)
			;

		byte[] mType = assistant.getOut().remove();
		String msgType = (new SerializedObject<String>()).fromByteStream(mType);
		byte[] msg = assistant.getOut().remove();
		// Message receivedMessage = msgFac.getMessage(msgType);
		String receivedMessage = (new SerializedObject<String>()).fromByteStream(msg);
		if (msgType.equals("LoginRequest") && receivedMessage.equals("Login Successfull")) {
			System.out.println("Successful Login");
			return true;
		}
		System.out.println("Log in failed");
		return false;
	}

	public void run()
	{
		
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Client ibrar = new Client("localhost", 5000);

	}

}

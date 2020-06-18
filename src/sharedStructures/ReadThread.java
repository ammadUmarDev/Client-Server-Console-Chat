package sharedStructures;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/*
 * maqsad of readThread: receive input from a socket and put it in a queue, the owner examines the queue when they free
 *
 * */
public class ReadThread extends Thread {
	private DataInputStream in;
	private Queue<byte[]> out;

	public Queue<byte[]> getOut() {
		return out;
	}

	public ReadThread(Socket inputSocket) throws IOException {
		this.in = new DataInputStream(inputSocket.getInputStream());
		this.out = new LinkedList<byte[]>();
	}

	public synchronized void execution() {
		try {
			byte[] array = new byte[25 * 1024 * 1024]; // max size is 25mb
			in.read(array); // read from stream
			out.add(array); // throw it in the queue
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			execution();
		}
	}
}

package serverfiles;

import java.io.IOException;
import java.net.Socket;

public class connectionthread extends Thread {
	ClientThread c1;
	ClientThread c2;
	public connectionthread(ClientThread c1,ClientThread c2)
	{
		this.c1=c1;
		this.c2=c2;
	}
	public void readthread() throws IOException
	{
		//this.c1.readthreadon(c2.acceptSocket);
		//this.c2.readthreadon(c1.acceptSocket);
	}
	public void run()
	{
		try {
			//this.outputstreamcalling();
			this.readthread();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}


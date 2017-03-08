package p2p.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class P2PClient {

	public static void main(String args[]) throws UnknownHostException, IOException{
		
		Socket s = new Socket("127.0.0.1",7734);
		PrintStream outputStream = new PrintStream(s.getOutputStream());
		outputStream.print("Hello server");
		return;
		
	}
}

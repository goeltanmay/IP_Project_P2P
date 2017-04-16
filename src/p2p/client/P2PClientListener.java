package p2p.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import p2p.server.Client;
import p2p.server.ClientRequest;
import p2p.server.P2PParser;

public class P2PClientListener implements Runnable{
	Socket s;
	public P2PClientListener(Socket socket){
		this.s = socket;
	}
	public void run() {
		int clientPort=0;
		String request;
		DataInputStream din;
		PrintStream output = null;
		try {
			clientPort= s.getPort();
			din = new DataInputStream(s.getInputStream());
			output= new PrintStream(s.getOutputStream());
			String clientportname = Integer.toString(clientPort);
			request = din.readLine();
			while(request != null){
				System.out.println(request);
			}
			
	 	} catch (IOException e) {
	 		e.printStackTrace();
	 	}
	}

}

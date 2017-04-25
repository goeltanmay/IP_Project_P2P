package p2p.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class P2PClientConnectionHandler implements Runnable {
	Socket s;
	P2PServerDataInterface p2pData;
	public P2PClientConnectionHandler(Socket s, P2PServerDataInterface p2pData) {
		this.s = s;
		this.p2pData = p2pData;
	}
	
	@Override
	public void run() {
		String request;
		Client client;
		DataInputStream din;
		PrintStream output = null;
	 try {
			din = new DataInputStream(s.getInputStream());
			output= new PrintStream(s.getOutputStream());
			request = din.readLine();
			ClientRequest req = P2PParser.parse(request);
			
			client = new Client(s.getInetAddress().getHostAddress() , req.clientPort);
			p2pData.addPeer(client);
			System.out.println("Peer Added");
			
			while(request != null){
				req = P2PParser.parse(request);
				client.clientport = req.clientPort;
				String response = req.handle(p2pData);
				System.out.println(response);
				output.println(response);
				output.flush();
				request = din.readLine();
			}
			
			p2pData.removePeer(client);
			System.out.println("Peer Removed");
			s.close();
			
	 	} catch (IOException e) {
	 		e.printStackTrace();
	 	}
	}

}

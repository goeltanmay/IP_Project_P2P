package p2p.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
		// TODO Auto-generated method stub
		int clientPort=0;
		String hostname;
		DataInputStream din;
//		ObjectInputStream input = null;
		ObjectOutputStream output = null;
	 try {
			clientPort= s.getPort();
			din = new DataInputStream(s.getInputStream());
			output= new ObjectOutputStream(s.getOutputStream());
			String clientportname = Integer.toString(clientPort);
			hostname = din.readLine();
			while(hostname != null){
				System.out.println(hostname);
				p2pData.addPeer(new Client(hostname, clientportname));
				System.out.println("Peer Added");
				System.out.println(this);
				hostname = din.readLine();
			}
			
			System.out.println("connection closed");
	 	} catch (IOException e) {
	 		e.printStackTrace();
	 	}
	}

}

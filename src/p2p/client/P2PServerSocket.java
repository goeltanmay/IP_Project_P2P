package p2p.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class P2PServerSocket implements Runnable{

	ServerSocket s;
	String folderName;
	public P2PServerSocket(ServerSocket s, String folderName){
		this.s = s;
		this.folderName = folderName;
	}
	public void run() {
		while (true){
			try {
				Socket clientSocket  = s.accept();
				Thread t = new Thread(new P2PClientListener(clientSocket, folderName));
				t.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}

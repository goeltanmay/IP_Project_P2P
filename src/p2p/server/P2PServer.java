package p2p.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class P2PServer {

	public static void main(String args[]) throws IOException{
		System.out.println(" Hello World ");
		ServerSocket listener = new ServerSocket(7734);
		Socket s = listener.accept();
		
		DataInputStream din = new DataInputStream(s.getInputStream());
		BufferedReader buff = new BufferedReader(new InputStreamReader(s.getInputStream()));
		System.out.println(buff.readLine());
		return;
	}
}

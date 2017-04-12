package p2p.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;import sun.print.resources.serviceui;

public class P2PServer implements P2PServerDataInterface{
	public static volatile List<RFC> rfcs;
	public static volatile List<Client> peers;
	ServerSocket listener;
	//public static List<ConnectionHandler> threads;
	
	public static void main(String args[]) throws IOException {
		rfcs = new LinkedList<>();
		peers = new LinkedList<>();
		int port = 7734;
		new P2PServer(port);
	}
	
	public P2PServer(int port) throws IOException {
		this.listener = new ServerSocket(7734);
		while(true){
			Socket s = listener.accept();
			Thread t = new Thread(new P2PClientConnectionHandler(s, this));
			t.start();
		}
	}


	@Override
	public boolean addPeer(Client client) {
		// TODO Auto-generated method stub
//		System.out.println(client.toString());
		return peers.add(client);
	}
}


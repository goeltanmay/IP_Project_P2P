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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;import sun.print.resources.serviceui;

public class P2PServer implements P2PServerDataInterface{
	public static volatile HashMap<RFC, List<Client>> rfcs;
	public static volatile List<Client> peers;
	ServerSocket listener;
	//public static List<ConnectionHandler> threads;
	
	public static void main(String args[]) throws IOException {
		rfcs = new HashMap<>();
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
		return peers.add(client);
	}

	@Override
	public boolean removePeer(Client client) {
		for (RFC i : rfcs.keySet()){
			if (rfcs.get(i).contains(client))
				rfcs.get(i).remove(client);
		}
		
		return peers.remove(client);
	}

	@Override
	public boolean addRfc(RFC rfc) {
		if(!rfcs.containsKey(rfc)) {
			rfcs.put(rfc, new LinkedList<Client>());
		}
		return true;
	}

	@Override
	public boolean addRFCClient(RFC rfc, Client client) {
		return rfcs.get(rfc).add(client);
	}

	@Override
	public List<Client> lookupRFC(RFC rfc) {
		if (rfcs.containsKey(rfc))
			return rfcs.get(rfc);
		return null;
	}

	@Override
	public HashMap<RFC, List<Client>> listAll() {
		// TODO Auto-generated method stub
		return rfcs;
	}

	
}


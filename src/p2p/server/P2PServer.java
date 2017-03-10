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

public class P2PServer implements Runnable{
	public static volatile List<RFC> rfcs;
	public static volatile List<Client> peers;
	ServerSocket listener;
	//public static List<ConnectionHandler> threads;
	
	public static void main(String args[]) throws IOException{
		rfcs = new LinkedList<>();
		peers = new LinkedList<>();
		int port = 7734;
		 new P2PServer(port);
		 }
	public P2PServer(int port) throws IOException{
		ServerSocket listener = new ServerSocket(7734);
		new Thread(this).start();
		}
		
			
			// code for reading the client's message
			//Socket s = listener.accept();
			//DataInputStream din = new DataInputStream(s.getInputStream());
			//BufferedReader buff = new BufferedReader(new InputStreamReader(din));
			//ClientRequest c = P2PParser.parse(buff.readLine());
			
			//System.out.println(c.toString());
			//DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
			//OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
			//BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			//bufferedWriter.write("got the message\n");
			//bufferedWriter.flush();
			//s.close();
			
		
//	listener.close();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket s;
		int clientPort=0;
		String hostname;
		DataInputStream din;
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
	 try {
		s= listener.accept();
		new Thread(this).start();
		clientPort= s.getPort();
		input = new ObjectInputStream(s.getInputStream());
		output= new ObjectOutputStream(s.getOutputStream());
		String clientportname = Integer.toString(clientPort);
		hostname = (input.readObject()).toString();
		peers.add(new Client(hostname,clientportname));
		System.out.println("Peer Added");
	} catch (IOException | ClassNotFoundException e) {
		e.printStackTrace();
	} 
	}
	}


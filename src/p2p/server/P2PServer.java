package p2p.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.stream.Stream;import sun.print.resources.serviceui;

public class P2PServer {

	public static void main(String args[]) throws IOException{
		System.out.println(" Hello World ");
		ServerSocket listener = new ServerSocket(7734);
		while(true){
			
			// code for reading the client's message
			Socket s = listener.accept();
			DataInputStream din = new DataInputStream(s.getInputStream());
			BufferedReader buff = new BufferedReader(new InputStreamReader(din));
			System.out.println(buff.readLine());
			System.out.println("here");
			DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			bufferedWriter.write("got the message\n");
			bufferedWriter.flush();
			s.close();
			
		}
//	listener.close();
	}
}

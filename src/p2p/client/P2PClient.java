package p2p.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.stream.Stream;

public class P2PClient {

	public static void main(String args[]) throws UnknownHostException, IOException{
		
		Socket s = new Socket("127.0.0.1",7734);
//		s.setKeepAlive(true);
		PrintStream outputStream = new PrintStream(s.getOutputStream());
		InputStream inputStream = s.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		
		BufferedReader buff = new BufferedReader(inputStreamReader);
		outputStream.println("Hello server\n");
		outputStream.flush();
	
		
		System.out.println(buff.readLine());
		s.close();
		
	}
}

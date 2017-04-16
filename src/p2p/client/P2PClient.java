package p2p.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

import p2p.client.util.Methods;
import p2p.client.util.P2PPacket;
import p2p.client.util.Version;

public class P2PClient {

	public static void main(String args[]) throws UnknownHostException, IOException{
		
		Random ran = new Random();
		int serverPort = ran.nextInt(50000)+10000; 
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(serverPort);
			Thread t = new Thread(new P2PServerSocket(serverSocket));
			t.start();
			System.out.println("Listening at port " + Integer.toString(serverPort));
		} catch(Exception e) {
			serverPort = ran.nextInt(50000)+10000;
			serverSocket = new ServerSocket(serverPort);
			Thread t = new Thread(new P2PServerSocket(serverSocket));
			System.out.println("Listening at port " + Integer.toString(serverPort));
			t.start();
		}
		
		Socket s = new Socket("127.0.0.1",7734);
		
		PrintStream outputStream = new PrintStream(s.getOutputStream());
		InputStream inputStream = s.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader buff = new BufferedReader(inputStreamReader);
		P2PPacket packet = new P2PPacket(Methods.ADD, 2177, Version.version);
		packet.addHeader("HOST", "127.0.0.1");
		packet.addHeader("PORT", Integer.toString(serverPort));
		packet.addHeader("TITLE", "Akriti's RFC 2");
		System.out.println(packet.toString());
		outputStream.println(packet.toString());
		outputStream.flush();
		
		System.out.println(buff.readLine());
		s.close();
		
	}
}

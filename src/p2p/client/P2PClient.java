package p2p.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

import p2p.client.util.Methods;
import p2p.client.util.P2PPacket;
import p2p.client.util.Version;

public class P2PClient {
	private static final String FILENAME = "/Users/tanmaygoel/clientfolders/";
	public static void main(String args[]) throws UnknownHostException, IOException{
		System.out.println("Which folder does this client have? ");
		Scanner scanner = new Scanner(System.in);
		Integer foldernumber = scanner.nextInt();
		String folderName = FILENAME + foldernumber.toString() + "/";
		
			
		
		Random ran = new Random();
		int serverPort = ran.nextInt(50000)+10000; 
		ServerSocket serverSocket;
		Thread t;
		try {
			serverSocket = new ServerSocket(serverPort);
			t = new Thread(new P2PServerSocket(serverSocket, folderName));
			t.start();
			System.out.println("Listening at port " + Integer.toString(serverPort));
		} catch(Exception e) {
			serverPort = ran.nextInt(50000)+10000;
			serverSocket = new ServerSocket(serverPort);
			t = new Thread(new P2PServerSocket(serverSocket, folderName));
			System.out.println("Listening at port " + Integer.toString(serverPort));
			t.start();
		}
		
		Socket s = new Socket("127.0.0.1",7734);
		
		PrintStream outputStream = new PrintStream(s.getOutputStream());
		InputStream inputStream = s.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader buff = new BufferedReader(inputStreamReader);
		int n=1;
		
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		for (int i=1; i<listOfFiles.length; i++){
			Integer rfcNo = Integer.parseInt(listOfFiles[i].getName().substring(3, (int) (listOfFiles[i].getName().length()-4)));
			P2PPacket packet = new P2PPacket(Methods.ADD, rfcNo, Version.version);
			packet.addHeader("HOST", "127.0.0.1");
			packet.addHeader("PORT", Integer.toString(serverPort));
			packet.addHeader("TITLE", listOfFiles[i].getName());
			outputStream.println(packet.toString());
			outputStream.flush();
			System.out.println(buff.readLine());
		}
		
		while(n <= 3 && n >= 1){
			System.out.println(" ------- Main Menu ------- ");
			System.out.println(" 1. List all RFCs at Server ");
			System.out.println(" 2. Lookup RFC at server");
			System.out.println(" 3. Get an RFC from P2P peer ");
			System.out.println(" Other.  EXIT! ");
			
			n = scanner.nextInt();
			switch(n) {
				case 1:
					P2PPacket packet1 = new P2PPacket(Methods.LIST, 2178, Version.version);
					packet1.addHeader("HOST", "127.0.0.1");
					packet1.addHeader("PORT", Integer.toString(serverPort));
					packet1.addHeader("TITLE", "Akriti's RFC 3");
					outputStream.println(packet1.toString());
					outputStream.flush();
					System.out.println(buff.readLine());
					break;
				case 2:
					System.out.println(" RFC Number to lookup : ");
					int rfcno = scanner.nextInt();
					P2PPacket packet2 = new P2PPacket(Methods.LOOKUP, rfcno, Version.version);
					packet2.addHeader("HOST", "127.0.0.1");
					packet2.addHeader("PORT", Integer.toString(serverPort));
					packet2.addHeader("TITLE", "Akriti's RFC 2");
					outputStream.println(packet2.toString());
					outputStream.flush();
					System.out.println(buff.readLine());
					break;
				case 3 :
					// TODO : implement P2P interaction;
					System.out.println(" RFC Number to lookup : ");
					int rfcno1 = scanner.nextInt();
					System.out.println(" IP address of the host : ");
					String ip = scanner.next();
					System.out.println(" Port number : ");
					int port = scanner.nextInt();
					
					Socket s2 = new Socket(ip,port);
					
					PrintStream outputStream2 = new PrintStream(s2.getOutputStream());
					InputStream inputStream2 = s2.getInputStream();
					InputStreamReader inputStreamReader2 = new InputStreamReader(inputStream2);
					BufferedReader buff2 = new BufferedReader(inputStreamReader2);
					
					P2PPacket packet3 = new P2PPacket(Methods.DOWNLOAD, rfcno1, Version.version);
					packet3.addHeader("HOST", "127.0.0.1");
					packet3.addHeader("PORT", Integer.toString(s2.getLocalPort()));
					
					outputStream2.println(packet3.toString());
					String response = buff2.readLine();
					System.out.println(response);
					break;
				default : break;
			}
		}
		t.stop();
		s.close();
		scanner.close();
		
	}
}

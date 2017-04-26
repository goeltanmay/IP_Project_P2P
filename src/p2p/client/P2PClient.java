package p2p.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
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
		
		
		
		System.out.println("What is the IP address of the server ");
		String serverIPAddress = scanner.next();
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
		
		Socket s = new Socket(serverIPAddress, 7734);
		String localIPAddress = s.getLocalAddress().getHostAddress();
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
			packet.addHeader("HOST", localIPAddress);
			packet.addHeader("PORT", Integer.toString(serverPort));
			packet.addHeader("TITLE", listOfFiles[i].getName());
			outputStream.println(packet.toString());
			outputStream.flush();
			System.out.println(buff.readLine());
		}
		
		while(n <= 4 && n >= 1){
			System.out.println(" ------- Main Menu ------- ");
			System.out.println(" 1. List all RFCs at Server ");
			System.out.println(" 2. Lookup RFC at server");
			System.out.println(" 3. Get an RFC from P2P peer ");
			System.out.println(" 4. Add RFC on server ");
			System.out.println(" Other.  EXIT! ");
			
			n = scanner.nextInt();
			switch(n) {
				case 1:
					P2PPacket packet1 = new P2PPacket(Methods.LIST, 2178, Version.version);
					packet1.addHeader("HOST", localIPAddress);
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
					packet2.addHeader("HOST", localIPAddress);
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
					DataInputStream inputStreamReader2 = new DataInputStream(inputStream2);
					
					P2PPacket packet3 = new P2PPacket(Methods.DOWNLOAD, rfcno1, Version.version);
					packet3.addHeader("HOST", localIPAddress);
					packet3.addHeader("PORT", Integer.toString(s2.getLocalPort()));
					
					outputStream2.println(packet3.toString());
					String response = inputStreamReader2.readUTF();
					String[] lines2 = response.split("<cr><lf>");
					String[] headers = lines2[0].split("<sp>");
					if(headers[1].equals("200")){
						String data = "";
						for(int i=2; i<lines2.length; i++){
							data += lines2[i];
						}
						try{
						    PrintWriter writer = new PrintWriter(folderName + "/rfc"+rfcno1+".txt" , "UTF-8");
						    writer.println(data);
						    writer.close();
						    P2PPacket packet = new P2PPacket(Methods.ADD, rfcno1, Version.version);
							packet.addHeader("HOST", localIPAddress);
							packet.addHeader("PORT", Integer.toString(serverPort));
							packet.addHeader("TITLE", "/rfc"+rfcno1+".txt");
							outputStream.println(packet.toString());
							outputStream.flush();
							System.out.println(buff.readLine());
						} catch (IOException e) {
						   // do something
						}
					}
					
					break;
				case 4:
					System.out.println(" RFC number that you want to add : ");
					Integer rfcNo = scanner.nextInt();
					P2PPacket packet = new P2PPacket(Methods.ADD, rfcNo, Version.version);
					packet.addHeader("HOST", localIPAddress);
					packet.addHeader("PORT", Integer.toString(serverPort));
					packet.addHeader("TITLE", "rfc" + rfcNo +".txt");
					outputStream.println(packet.toString());
					outputStream.flush();
					System.out.println(buff.readLine());
					break;
				default : break;
			}
		}
		t.stop();
		s.close();
		scanner.close();
		
	}
}

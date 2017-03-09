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

import p2p.client.util.Methods;
import p2p.client.util.P2PPacket;
import p2p.client.util.Version;

public class P2PClient {

	public static void main(String args[]) throws UnknownHostException, IOException{
		
		Socket s = new Socket("127.0.0.1",7734);
//		s.setKeepAlive(true);
		PrintStream outputStream = new PrintStream(s.getOutputStream());
		InputStream inputStream = s.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		
		BufferedReader buff = new BufferedReader(inputStreamReader);
		P2PPacket packet = new P2PPacket(Methods.ADD, 2177, Version.version);
		packet.addHeader("HOST", "127.0.0.1");
		packet.addHeader("PORT", Integer.toString(s.getPort()));
		packet.addHeader("TITLE", "Akriti's RFC");
		outputStream.println(packet.toString());
		outputStream.flush();
	
		
		System.out.println(buff.readLine());
		s.close();
		
	}
}

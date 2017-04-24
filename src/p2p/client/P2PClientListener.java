package p2p.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Time;
import java.util.Calendar;

import p2p.client.util.P2PPacket;
import p2p.client.util.P2PResponse;
import p2p.server.Client;
import p2p.server.ClientRequest;
import p2p.server.P2PParser;

public class P2PClientListener implements Runnable{
	Socket s;
	String folderName;
	public P2PClientListener(Socket socket, String folder){
		this.s = socket;
		this.folderName = folder;
	}
	public void run() {
		int clientPort=0;
		String request;
		DataInputStream din;
		DataOutputStream output = null;
		try {
			clientPort= s.getPort();
			din = new DataInputStream(s.getInputStream());
			output= new DataOutputStream(s.getOutputStream());
			String clientportname = Integer.toString(clientPort);
			request = din.readLine();
			
			String[] lines = request.split("<cr><lf>");
			String[] firstLine = lines[0].split("<sp>");
			ClientRequest req = new ClientRequest(firstLine[0], Integer.parseInt(firstLine[1]), firstLine[2]);
			for (int i=1; i<lines.length; i++){
				String[] line = lines[i].split("<sp>");
				req.addHeader(line[0], line[1]);
				if(line[0].equalsIgnoreCase("port"))
					req.clientPort = line[1];
			}
			File folder = new File(this.folderName);
			File[] listOfFiles = folder.listFiles();
			P2PResponse response = new P2PResponse();
			boolean found = false;
			for(int i=0; i< listOfFiles.length; i++){
				if(listOfFiles[i].getName().equals("rfc" + req.rfc_number.toString() + ".txt")){
					FileReader f = new FileReader(listOfFiles[i].getPath());
					BufferedReader buff = new BufferedReader(f);
					found = true;
					response.status_code=200;
					response.version = "P2P-CI/1.0";
					response.time = Calendar.getInstance().getTime();
					response.data = "";
					String line = "";
					while((line = buff.readLine()) != null) {
		                response.data += line + "\n";
		            }
					buff.close();
					f.close();
				}
			}
			if(!found)
				response.status_code=404;
			
			output.writeUTF(response.toString());
			
			s.close();
	 	} catch (IOException e) {
	 		e.printStackTrace();
	 	}
	}

}

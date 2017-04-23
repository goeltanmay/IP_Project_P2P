package p2p.server;

import p2p.client.util.P2PResponse;

public class P2PParser {
	public static ClientRequest parse(String input){
		String[] lines = input.split("<cr><lf>");
		String[] firstLine = lines[0].split("<sp>");
		ClientRequest req = new ClientRequest(firstLine[0], Integer.parseInt(firstLine[1]), firstLine[2]);
		for (int i=1; i<lines.length; i++){
			String[] line = lines[i].split("<sp>");
			req.addHeader(line[0], line[1]);
			if(line[0].equalsIgnoreCase("port"))
				req.clientPort = line[1];
		}
		return req;
	}
	
}

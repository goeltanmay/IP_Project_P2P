package p2p.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import p2p.client.util.P2PHeader;

public class ClientRequest {
	String method;
	public Integer rfc_number;
	String version;
	public String clientPort;
	List<P2PHeader> headers;
	
	public ClientRequest(String method, int rfc_number, String version){
		this.method = method;
		this.rfc_number = rfc_number;
		this.version = version;
		this.headers = new ArrayList<>();
		
	}
	
	public void addHeader(String headerType, String value){
		headers.add(new P2PHeader(headerType, value));
	}
	
	public String toString(){
		String s = String.format("%s<sp>%d<sp>%s<cr><lf>", method, rfc_number, version );
		for(P2PHeader p : headers)
			s = s + p.field_name + "<sp>" + p.value + "<cr><lf>";
		s = s + "<cr><lf>";
		return s;
	}
	
	public String handle(P2PServerDataInterface p2pData){
		
		P2PResponse response = new P2PResponse();
		response.version = "P2P-CI/1.0";
		if (!this.version.equals("P2P-CI/1.0")){
			response.status_code = "505";
			response.description = "P2P-CI Version Not Supported";
			return response.toString();
		}
		
		if (this.method.equalsIgnoreCase("add")) {
			RFC rfc = new RFC();
			rfc.rfc_number = this.rfc_number;
			Client client = new Client();
			
			for( P2PHeader i : headers){
				if (i.field_name.equalsIgnoreCase("title")){
					rfc.rfc_title = i.value;
				}
				
				if (i.field_name.equalsIgnoreCase("host")){
					client.hostname = i.value;
				}
				
				if (i.field_name.equalsIgnoreCase("port")){
					client.clientport = i.value;
				}
			}
			p2pData.addRfc(rfc);
			p2pData.addRFCClient(rfc, client);
			response.status_code = "200";
			response.description = "OK";
			response.data.add(rfc.toString() + "<sp>" + client.toString());
		}
		
		else if (this.method.equalsIgnoreCase("lookup")) {
			response.status_code = "200";
			response.description = "OK";
			RFC rfc = new RFC();
			rfc.rfc_number = this.rfc_number;
			Client client = new Client();
			
			for( P2PHeader i : headers){
				if (i.field_name.equalsIgnoreCase("title")){
					rfc.rfc_title = i.value;
				}
				
				if (i.field_name.equalsIgnoreCase("host")){
					client.hostname = i.value;
				}
				
				if (i.field_name.equalsIgnoreCase("port")){
					client.clientport = i.value;
				}
			}
			
			List<Client> results = p2pData.lookupRFC(rfc);
			if (results == null || results.isEmpty()){
				response.status_code = "404";
				response.description = "NOT FOUND";
				return response.toString();
			}
			else {
				for (Client i : results){
					response.data.add( rfc.toString() + "<sp>" + i.toString() );
				}
				return response.toString();
			}
		}
		
		else if (this.method.equalsIgnoreCase("list")) {
			response.status_code = "200";
			response.description = "OK";
			HashMap<RFC, List<Client>> data = p2pData.listAll();
			for (RFC i : data.keySet()){
				for (Client c : data.get(i)){
					response.data.add(i.toString() + "<sp>" + c.toString());
				}
			}
		}
		
		return response.toString();
	}
	
}

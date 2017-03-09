package p2p.server;

import java.util.ArrayList;
import java.util.List;

import p2p.client.util.P2PHeader;

public class ClientRequest {
	String method;
	Integer rfc_number;
	String version;
	
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
	
}

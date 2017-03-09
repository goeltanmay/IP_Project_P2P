package p2p.client.util;

import java.util.ArrayList;
import java.util.List;

public class P2PPacket {
	String method;
	Integer rfc_number;
	String version;
	
	List<P2PHeader> headers;
	
	public P2PPacket(String method, int rfc_number, String version){
		this.method = method;
		this.rfc_number = rfc_number;
		this.version = version;
		this.headers = new ArrayList<P2PHeader>();
	}
	
	public void addHeader(String headerType, String value){
		headers.add(new P2PHeader(headerType, value));
	}
	
	public String toString(){
		String s = String.format("%s<cr>%d<cr>%s<cr><lf>", method, rfc_number, version );
		for(P2PHeader p : headers)
			s = s + p.field_name + "<cr>" + p.value + "<cr><lf>";
		s = s + "<cr><lf>";
		return s;
	}
	
}

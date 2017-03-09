package p2p.client.util;

public class P2PHeader {
	public String field_name;
	public String value;
	
	public P2PHeader(String headerName, String value){
		this.field_name = headerName;
		this.value = value;
	}
}

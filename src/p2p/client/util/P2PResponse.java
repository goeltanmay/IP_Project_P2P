package p2p.client.util;

import java.util.Date;

public class P2PResponse {
//	P2P-CI/1.0 200 OK
//	Date: Wed, 12 Feb 2009 15:12:05 GMT
//	OS: Mac OS 10.2.1
//	Last-Modified: Thu, 21 Jan 2001 9:23:46 GMT
//	Content-Length: 12345
//	Content-Type: text/text
//	(data data data ...) 
	
	public String version;
	public Integer status_code;
	public Date time;
	public String data;
	
	public String toString(){
		String response = version + "<sp>";
		response += status_code.toString() + "<sp>";
		switch(status_code){
			case 200 : response += "OK";
						break;
			case 404 : response += "NOT FOUND" ; 
						break;
		}
		response += "<cr><lf>";
		response += "<cr><lf>";
		response += data;
		response += "<cr><lf>";
		return response;
	}
	
	public static P2PResponse parseResponse(){
		return null;
	}
}

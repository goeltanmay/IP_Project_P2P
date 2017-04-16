package p2p.server;

import java.util.ArrayList;
import java.util.List;

public class P2PResponse {
	public String status_code;
	public String description;
	public String version;
	public List<String> data = new ArrayList<>();
	
	public String toString(){
		String response = version + "<sp>" + status_code + "<sp>" + description + "<cr><lf>";
		for (String i : data){
			response = response + data + "<cr><lf>";
		}
		
		return response;
	}
}

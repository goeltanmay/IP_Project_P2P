package p2p.server;

public class Client {
	public String hostname;
	public String clientport;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientport == null) ? 0 : clientport.hashCode());
		result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (clientport == null) {
			if (other.clientport != null)
				return false;
		} else if (!clientport.equals(other.clientport))
			return false;
		if (hostname == null) {
			if (other.hostname != null)
				return false;
		} else if (!hostname.equals(other.hostname))
			return false;
		return true;
	}
	
	public Client(String hostname, String clientportname){
		this.hostname= hostname;
		this.clientport=clientportname;
	}
	
	public Client() {
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return this.hostname + "<sp>" + this.clientport;
	}
}

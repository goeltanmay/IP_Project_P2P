package p2p.server;

public class RFC {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rfc_number == null) ? 0 : rfc_number.hashCode());
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
		RFC other = (RFC) obj;
		if (rfc_number == null) {
			if (other.rfc_number != null)
				return false;
		} else if (!rfc_number.equals(other.rfc_number))
			return false;
		return true;
	}
	
	public Integer rfc_number;
	public String rfc_title;
	
	public String toString() {
		return this.rfc_number.toString() + "<sp>" +  this.rfc_title ;
	}
}

package p2p.server;

import java.util.HashMap;
import java.util.List;

public interface P2PServerDataInterface {
	
	public boolean addPeer(Client client);

	public boolean removePeer(Client client);
	
	public boolean addRfc(RFC rfc);
	
	public boolean addRFCClient(RFC rfc, Client client);
	
	public List<Client> lookupRFC(RFC rfc);
	
	public RFC getRFC(RFC rfc);
	
	public HashMap<RFC,List<Client>> listAll();
		
}

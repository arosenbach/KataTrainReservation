package coltrain;

import javax.ws.rs.client.Client;

public interface BookingReferenceService {

    String getBookRef(Client client);
}

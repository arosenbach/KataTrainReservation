package coltrain;

import javax.ws.rs.client.Client;

public interface BookingRefService {

    String getBookRef(Client client);
}

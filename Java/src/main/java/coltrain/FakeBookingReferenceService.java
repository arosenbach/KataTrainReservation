package coltrain;

import javax.ws.rs.client.Client;

public class FakeBookingReferenceService implements BookingReferenceService {


    @Override
    public String getBookRef(Client client) {
        return "bookRef";
    }
}

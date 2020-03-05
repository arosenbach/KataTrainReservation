package coltrain;

import javax.ws.rs.client.Client;

public class FakeBookingRefService implements BookingRefService {


    @Override
    public String getBookRef(Client client) {
        return "bookRef";
    }
}

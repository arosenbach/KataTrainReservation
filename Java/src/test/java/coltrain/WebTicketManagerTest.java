package coltrain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WebTicketManagerTest {

    @Test
    public void reserve_givenZeroSeats_itShouldReturnNoSeats() {
        final BookingRefService bookingRefService = new FakeBookingRefService();
        WebTicketManager sut = new WebTicketManager(bookingRefService);
        String train = "express_2000";
        int nbSeats = 0;

        String reservation = sut.reserve(train, nbSeats);
        assertEquals("{\"trainId\": \"express_2000\",\"bookingReference\": \"bookRef\",\"seats\":[]}", reservation);
    }
}

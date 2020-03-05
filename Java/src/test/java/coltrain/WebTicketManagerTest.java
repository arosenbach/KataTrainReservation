package coltrain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WebTicketManagerTest {

    @Test
    public void reserve_givenZeroSeats_itShouldReturnNoSeats() {
        final BookingReferenceService bookingReferenceService = new FakeBookingReferenceService();
        final TrainDataService trainDataService = new FakeTrainDataService();
        final WebTicketManager sut = new WebTicketManager(bookingReferenceService, trainDataService);
        final String train = "express_2000";
        final int nbSeats = 0;

        final String reservation = sut.reserve(train, nbSeats);
        assertEquals("{\"trainId\": \"express_2000\",\"bookingReference\": \"bookRef\",\"seats\":[]}", reservation);
    }

    @Test
    public void reserve_givenOneSeat_itShouldReturnOneSeat() {
        final BookingReferenceService bookingReferenceService = new FakeBookingReferenceService();
        final TrainDataService trainDataService = new FakeTrainDataService();
        final WebTicketManager sut = new WebTicketManager(bookingReferenceService, trainDataService);
        final String train = "express_2000";
        final int nbSeats = 1;

        final String reservation = sut.reserve(train, nbSeats);
        assertEquals("{\"trainId\": \"express_2000\",\"bookingReference\": \"bookRef\",\"seats\":[\"1A\"]}", reservation);
    }
}

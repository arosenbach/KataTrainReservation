package coltrain;

import domain.models.Reservation;
import domain.models.Seat;
import domain.service.WebTicketManager;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WebTicketManagerTest {

    @Test
    public void reserve_givenZeroSeatsRequested_itShouldReturnNoSeats() {
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(TrainTopology.TRAIN_TWO_SEATS_NOT_RESERVED), new FakeBookingReferenceService());
        final Reservation actual = sut.reserve("express2000", 0);
        assertEquals(Collections.<Seat>emptyList(), actual.getAvailableSeats());
    }

    @Test
    public void reserve_givenOneSeatRequested_itShouldReturnOneSeat() {
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(TrainTopology.TRAIN_TWO_SEATS_NOT_RESERVED), new FakeBookingReferenceService());
        final Reservation reservation = sut.reserve("express2000", 1);
        final Reservation expected = new Reservation("express2000", Collections.singletonList(new Seat("A", 1)), "bookingReference");
        assertEquals(expected, reservation);
    }

    @Test
    public void reserve_givenMoreThanThresholdSeatsRequested_itShouldReturnNoSeats() {
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(TrainTopology.TRAIN_SIX_SEATS_NOT_RESERVED), new FakeBookingReferenceService());
        final Reservation reservation = sut.reserve("express2000", 5);
        final Reservation expected = new Reservation("express2000", Collections.emptyList(), "");
        assertEquals(expected, reservation);
    }

    @Ignore("business rule is not respected")
    @Test
    public void reserve_givenTwoSeatsRequested_itShouldReturnSeatsInTheSameCoach() {
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(TrainTopology.TRAIN_TWO_COACHES_SIX_SEATS_AND_ONE_SEAT_RESERVED), new FakeBookingReferenceService());
        final Reservation reservation = sut.reserve("express2000", 3);

        List<Seat> seats = Arrays.asList(new Seat("B", 4), new Seat("B", 5), new Seat("B", 6));
        seats.forEach(s->s.setBookingRef("bookingReference")); // inner to seat logic.
        Reservation expected =  new Reservation("express2000", seats, "");
        assertEquals(expected, reservation);
    }

}
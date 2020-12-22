package coltrain;

import controller.ReservationsController;
import domain.models.Reservation;
import domain.service.WebTicketManager;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegressionTest {

    @Test
    public void reserve_givenZeroSeatsRequested_itShouldReturnNoSeats() {
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(TrainTopology.TRAIN_TWO_SEATS_NOT_RESERVED), new FakeBookingReferenceService());
        final Reservation actual = sut.reserve("express2000", 0);
        assertEquals("{\"trainId\": \"express2000\",\"bookingReference\": \"bookingReference\",\"seats\":[]}", ReservationsController.toJsonString(actual));
    }

    @Test
    public void reserve_givenOneSeatRequested_itShouldReturnOneSeat() {
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(TrainTopology.TRAIN_TWO_SEATS_NOT_RESERVED), new FakeBookingReferenceService());
        final Reservation reservation = sut.reserve("express2000", 1);
        assertEquals("{\"trainId\": \"express2000\",\"bookingReference\": \"bookingReference\",\"seats\":[\"1A\"]}",ReservationsController.toJsonString(reservation));
    }

    @Test
    public void reserve_givenMoreThanThresholdSeatsRequested_itShouldReturnNoSeats() {
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(TrainTopology.TRAIN_SIX_SEATS_NOT_RESERVED), new FakeBookingReferenceService());
        final Reservation reservation = sut.reserve("express2000", 5);
        assertEquals("{\"trainId\": \"express2000\",\"bookingReference\": \"\",\"seats\":[]}", ReservationsController.toJsonString(reservation));
    }

    @Ignore("business rule is not respected")
    @Test
    public void reserve_givenTwoSeatsRequested_itShouldReturnSeatsInTheSameCoach() {
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(TrainTopology.TRAIN_TWO_COACHES_SIX_SEATS_AND_ONE_SEAT_RESERVED), new FakeBookingReferenceService());
        final Reservation reservation = sut.reserve("express2000", 3);
        assertEquals("{\"trainId\": \"express2000\", \"bookingReference\": \"\", \"seats\":[\"4B\", \"5B\", \"6B\"]}",ReservationsController.toJsonString(reservation));
    }
}

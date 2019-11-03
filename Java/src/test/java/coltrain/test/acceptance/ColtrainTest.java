package coltrain.test.acceptance;

import coltrain.domain.BookingReferenceService;
import coltrain.domain.Reservation;
import coltrain.domain.ReservationFailure;
import coltrain.domain.Train;
import coltrain.domain.TrainDataService;
import coltrain.infra.TrainDataServiceImpl;
import coltrain.WebTicketManager;
import coltrain.domain.Seat;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class ColtrainTest {

    private static final String BOOKING_REFERENCE = "75bcd15";
    private static final String EMPTY_BOOKING = "";
    private static final String TRAIN_ID = "express_2000";
    private static final Pattern SEAT_PATTERN = Pattern.compile("(\\d+)([A-Z])");

    @Test
    public void should_reserve_seats_when_train_is_empty() {
        final TrainDataService trainDataService = new FakeTrainDataService(TrainTopology.EMPTY_TRAIN);
        final FakeBookingReferenceService bookingReferenceService = new FakeBookingReferenceService(BOOKING_REFERENCE);
        final WebTicketManager sut = new WebTicketManager(trainDataService, bookingReferenceService);

        final Reservation reservation = sut.reserve(TRAIN_ID, 3);
        assertEquals(new Reservation(TRAIN_ID, BOOKING_REFERENCE, seats("1A","2A","3A")), reservation);
    }

    private List<Seat> seats(String... seats) {
        return Arrays.stream(seats)
                .map(SEAT_PATTERN::matcher)
                .peek(Matcher::find)
                .map(m -> new Seat(Integer.parseInt(m.group(1)),m.group(2)))
                .collect(toList());
    }

    @Test
    public void should_not_reserve_seats_when_train_is_70_percent_booked() {
        final TrainDataService trainDataService = new FakeTrainDataService(TrainTopology.WITH_10_SEATS_AND_6_ALREADY_BOOKED);
        final FakeBookingReferenceService bookingReferenceService = new FakeBookingReferenceService(BOOKING_REFERENCE);
        final WebTicketManager sut = new WebTicketManager(trainDataService, bookingReferenceService);

        final Reservation reservation = sut.reserve(TRAIN_ID, 3);
        assertEquals(new ReservationFailure(TRAIN_ID), reservation);
    }


    @Test
    public void should_reserve_all_seats_in_the_same_coach() {
        final TrainDataService trainDataService = new FakeTrainDataService(TrainTopology.WITH_COACH_A_HAVING_1_FREE_SEAT_AND_COACH_B_EMPTY);
        final FakeBookingReferenceService bookingReferenceService = new FakeBookingReferenceService(BOOKING_REFERENCE);
        final WebTicketManager sut = new WebTicketManager(trainDataService, bookingReferenceService);

        final Reservation reservation = sut.reserve(TRAIN_ID, 3);

        assertEquals(new Reservation(TRAIN_ID, BOOKING_REFERENCE, seats("1B", "2B", "3B")), reservation);
    }

    private class FakeTrainDataService implements TrainDataService {
        private String topology;

        private FakeTrainDataService(final String topology) {
            this.topology = topology;
        }

        @Override
        public Train getTrain(final String trainId) {
            return new Train(trainId, TrainDataServiceImpl.adaptTrainTopology(topology));
        }

        @Override
        public void bookSeats(final String trainId, final List<Seat> availableSeats, final String bookingRef) {

        }
    }

    private class FakeBookingReferenceService implements BookingReferenceService {
        private final String bookingReference;

        public FakeBookingReferenceService(final String bookingReference) {
            this.bookingReference = bookingReference;
        }

        @Override
        public String getBookingReference() {
            return bookingReference;
        }
    }
}
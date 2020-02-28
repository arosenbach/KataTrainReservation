package coltrain.test.acceptance;

import coltrain.WebTicketManager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColtrainTest {

    @Test
    public void reserve_givenZeroSeats_itShouldReturnNoSeats() {
        WebTicketManager sut = new WebTicketManager();
        String train = "express_2000";
        int nbSeats = 0;

        String reservation = sut.reserve(train, nbSeats);
        assertEquals("{\"trainId\": \"express_2000\",\"bookingReference\": \"75bcd17\",\"seats\":[]}", reservation);
    }

}
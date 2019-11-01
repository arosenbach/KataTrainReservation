package coltrain.test.acceptance;

import coltrain.WebTicketManager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColtrainTest {
    @Test
    public void should_reserve_seats_when_train_is_empty() {
        final WebTicketManager sut = new WebTicketManager();
        final String reservation = sut.reserve("express_2000", 3);

        assertEquals("{\"trainId\": \"express_2000\",\"bookingReference\": \"75bcd15\",\"seats\":[\"1A\", \"2A\", \"3A\"]}", reservation);
    }
}
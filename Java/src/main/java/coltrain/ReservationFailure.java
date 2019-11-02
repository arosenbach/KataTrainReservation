package coltrain;

import java.util.Collections;

public class ReservationFailure extends Reservation {
    public ReservationFailure(final String trainId) {
        super(trainId,"", Collections.emptyList());
    }
}

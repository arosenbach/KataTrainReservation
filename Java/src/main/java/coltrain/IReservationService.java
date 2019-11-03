package coltrain;

import coltrain.domain.Reservation;

public interface IReservationService {
    Reservation reserve(String trainId, int requestedSeatsCount);
}

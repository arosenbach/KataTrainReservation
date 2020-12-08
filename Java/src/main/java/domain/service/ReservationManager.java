package domain.service;

import domain.models.Reservation;

public interface ReservationManager {
    Reservation reserve(String trainId, int seats);
}

package service;

import models.Reservation;

public interface ReservationManager {
    Reservation reserve(String trainId, int seats);
}

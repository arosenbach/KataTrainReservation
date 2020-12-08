package coltrain;

import service.BookingReferenceService;

class FakeBookingReferenceService implements BookingReferenceService {
    @Override
    public String getBookRef() {
        return "bookingReference";
    }
}

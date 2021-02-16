package coltrain;

public class TrainTopology {
    public static final String TRAIN_TWO_SEATS_NOT_RESERVED =
            "{\"seats\": " +
                    "{\"1A\": {\"booking_reference\": \"\", \"seat_number\": \"1\", \"coach\": \"A\"}," +
                    "\"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}}}";

    public static final String TRAIN_SIX_SEATS_NOT_RESERVED =
            "{\"seats\": " +
                    "{\"1A\": {\"booking_reference\": \"\", \"seat_number\": \"1\", \"coach\": \"A\"}," +
                    "\"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}," +
                    "\"3A\": {\"booking_reference\": \"\", \"seat_number\": \"3\", \"coach\": \"A\"}," +
                    "\"4A\": {\"booking_reference\": \"\", \"seat_number\": \"4\", \"coach\": \"A\"}," +
                    "\"5A\": {\"booking_reference\": \"\", \"seat_number\": \"5\", \"coach\": \"A\"}," +
                    "\"6A\": {\"booking_reference\": \"\", \"seat_number\": \"6\", \"coach\": \"A\"}" +
                    "}" +
                    "}";

    public static final String TRAIN_TWO_COACHES_SIX_SEATS_AND_ONE_SEAT_RESERVED =
            "{\"seats\": " +
                    "{\"1A\": {\"booking_reference\": \"bookingRef\", \"seat_number\": \"1\", \"coach\": \"A\"}," +
                    "\"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}," +
                    "\"3A\": {\"booking_reference\": \"\", \"seat_number\": \"3\", \"coach\": \"A\"}," +
                    "\"4B\": {\"booking_reference\": \"\", \"seat_number\": \"4\", \"coach\": \"B\"}," +
                    "\"5B\": {\"booking_reference\": \"\", \"seat_number\": \"5\", \"coach\": \"B\"}," +
                    "\"6B\": {\"booking_reference\": \"\", \"seat_number\": \"6\", \"coach\": \"B\"}" +
                    "}" +
                    "}";


    public static final String TRAIN_TWO_COACHES_SIX_SEATS_AND_ALL_SEAT_RESERVED =
            "{\"seats\": " +
                    "{\"1A\": {\"booking_reference\": \"bookingRef\", \"seat_number\": \"1\", \"coach\": \"A\"}," +
                    "\"2A\": {\"booking_reference\": \"bookingRef\", \"seat_number\": \"2\", \"coach\": \"A\"}," +
                    "\"3A\": {\"booking_reference\": \"bookingRef\", \"seat_number\": \"3\", \"coach\": \"A\"}," +
                    "\"4B\": {\"booking_reference\": \"bookingRef\", \"seat_number\": \"4\", \"coach\": \"B\"}," +
                    "\"5B\": {\"booking_reference\": \"bookingRef\", \"seat_number\": \"5\", \"coach\": \"B\"}," +
                    "\"6B\": {\"booking_reference\": \"bookingRef\", \"seat_number\": \"6\", \"coach\": \"B\"}" +
                    "}" +
                    "}";

    public static final String TRAIN_SIX_SEATS_NOT_RESERVED_TWO_COACHES =
            "{\"seats\": " +
                    "{\"1A\": {\"booking_reference\": \"\", \"seat_number\": \"1\", \"coach\": \"A\"}," +
                    "\"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}," +
                    "\"3A\": {\"booking_reference\": \"\", \"seat_number\": \"3\", \"coach\": \"A\"}," +
                    "\"4B\": {\"booking_reference\": \"\", \"seat_number\": \"4\", \"coach\": \"B\"}," +
                    "\"5B\": {\"booking_reference\": \"\", \"seat_number\": \"5\", \"coach\": \"B\"}," +
                    "\"6B\": {\"booking_reference\": \"\", \"seat_number\": \"6\", \"coach\": \"B\"}" +
                    "}" +
                    "}";

}
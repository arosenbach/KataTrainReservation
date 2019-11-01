package coltrain.test.acceptance;

class TrainTopology {
    static final String EMPTY_TRAIN = "{\"seats\":{" +
            "\"1A\":{\"coach\":\"A\",\"seat_number\":\"1\",\"booking_reference\":\"\"}," +
            "\"2A\":{\"coach\":\"A\",\"seat_number\":\"2\",\"booking_reference\":\"\"}," +
            "\"3A\":{\"coach\":\"A\",\"seat_number\":\"3\",\"booking_reference\":\"\"}," +
            "\"4A\":{\"coach\":\"A\",\"seat_number\":\"4\",\"booking_reference\":\"\"}," +
            "\"5A\":{\"coach\":\"A\",\"seat_number\":\"5\",\"booking_reference\":\"\"}," +
            "\"6A\":{\"coach\":\"A\",\"seat_number\":\"6\",\"booking_reference\":\"\"}," +
            "\"7A\":{\"coach\":\"A\",\"seat_number\":\"7\",\"booking_reference\":\"\"}," +
            "\"8A\":{\"coach\":\"A\",\"seat_number\":\"8\",\"booking_reference\":\"\"}," +
            "\"1B\":{\"coach\":\"B\",\"seat_number\":\"1\",\"booking_reference\":\"\"}," +
            "\"2B\":{\"coach\":\"B\",\"seat_number\":\"2\",\"booking_reference\":\"\"}," +
            "\"3B\":{\"coach\":\"B\",\"seat_number\":\"3\",\"booking_reference\":\"\"}," +
            "\"4B\":{\"coach\":\"B\",\"seat_number\":\"4\",\"booking_reference\":\"\"}," +
            "\"5B\":{\"coach\":\"B\",\"seat_number\":\"5\",\"booking_reference\":\"\"}," +
            "\"6B\":{\"coach\":\"B\",\"seat_number\":\"6\",\"booking_reference\":\"\"}," +
            "\"7B\":{\"coach\":\"B\",\"seat_number\":\"7\",\"booking_reference\":\"\"}," +
            "\"8B\":{\"coach\":\"B\",\"seat_number\":\"8\",\"booking_reference\":\"\"}" +
            "}}";
    static final String WITH_10_SEATS_AND_6_ALREADY_BOOKED = "{\"seats\":{" +
            "\"1A\":{\"coach\":\"A\",\"seat_number\":\"1\",\"booking_reference\":\"123456\"}," +
            "\"2A\":{\"coach\":\"A\",\"seat_number\":\"2\",\"booking_reference\":\"123456\"}," +
            "\"3A\":{\"coach\":\"A\",\"seat_number\":\"3\",\"booking_reference\":\"123456\"}," +
            "\"4A\":{\"coach\":\"A\",\"seat_number\":\"4\",\"booking_reference\":\"123456\"}," +
            "\"5A\":{\"coach\":\"A\",\"seat_number\":\"5\",\"booking_reference\":\"123456\"}," +
            "\"1B\":{\"coach\":\"B\",\"seat_number\":\"1\",\"booking_reference\":\"123456\"}," +
            "\"2B\":{\"coach\":\"B\",\"seat_number\":\"2\",\"booking_reference\":\"\"}," +
            "\"3B\":{\"coach\":\"B\",\"seat_number\":\"3\",\"booking_reference\":\"\"}," +
            "\"4B\":{\"coach\":\"B\",\"seat_number\":\"4\",\"booking_reference\":\"\"}," +
            "\"5B\":{\"coach\":\"B\",\"seat_number\":\"5\",\"booking_reference\":\"\"}" +
            "}}";
}

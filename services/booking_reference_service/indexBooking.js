const express = require("express");
const app = express();
app.listen(8282, () => {
    console.log("Server running on port 8282");
});

let bookingRef = 123456789;
app.get("/booking_reference", (req, res, next) => {
    res.send(bookingRef.toString(16));
    bookingRef++;
});
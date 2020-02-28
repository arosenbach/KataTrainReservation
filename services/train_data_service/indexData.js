const express = require("express");
const app = express();
const bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

let trains = require('./trains.json');

function checkTrainId(trainId, res){
    if(!!trains[trainId]) {
        return true;
    }
    res.status(422).send('Invalid trainId');
    return false;
}

function checkSeats(trainId, seats, res){
    const trainSeats = trains[trainId].seats;
    for(let seat of seats){
        if(!trainSeats[seat]){
            res.status(422).send(`Invalid seat ${seat}`);
            return false;
        }

        if(trainSeats[seat].booking_reference !== ''){
            res.status(422).send(`Seat ${seat} already booked`);
            return false;
        }
    }
    return true;
}

function checkBookingReference(bookingReference) {
    return !!bookingReference;
}

app.listen(8181, () => {
    console.log("Server running on port 8181");
});

app.get("/data_for_train/:trainId", (req, res) => {
    res.json(trains[req.params.trainId]);
});

app.post('/reserve', function (req, res) {
    const {trainId, bookingReference, seats} = req.body;
    if(!checkTrainId(trainId,res)){
        return;
    }
    if(!checkSeats(trainId, seats, res)){
        return;
    }

    if(!checkBookingReference(bookingReference)){
        res.status(422).send(`Invalid Booking Ref "${bookingReference}"`);
        return;
    }
    const trainSeats = trains[trainId].seats;
    if(seats && bookingReference){
        for(let seat of seats){
            trainSeats[seat].booking_reference = bookingReference;
        }
    }
    res.json({trainId:trainId, bookingReference:bookingReference, seats: seats});
  })

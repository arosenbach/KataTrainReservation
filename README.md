# Kata: Train Reservation

Coltrain is a web application that provides users with a REST API to book train seats. The app relies on the railway operator API to :

* get a unique reservation number;
* get the topology of a train (coaches, seats identifier and availability);
* post the actual reservation.

**Call to these APIs are billed**; and the cost is high! Our customer complains about the current cost of our usage of the railway operator, despite the cache mechanism that was implemented to solve the issue. 

You're working on the "Coltrain" application. Your task is to:
 - understand why Coltrain does so many calls to the railway operator API;
 - see if we can reduce the number of API calls (and doing so, reducing the bill).

This kata was originally created by [Emily Bache](https://github.com/emilybache/KataTrainReservation)

## Business Rules around Reservations

There are various business rules and policies around which seats may be reserved. For a train, overall,
 **no more than 70% of seats may be reserved** in advance. Ideally, no individual coach should have 
 **more than 70% reserved seats either**. However, there is another business rule that says you 
 **must put all the seats for one reservation in the same coach**. This could make you and go over 70% for some coaches, 
 just make sure to keep to 70% for the whole train.

## Hexagonal Architecture

The ultimate goal of this kata is revamp the project into an [hexagonal architecture](https://softwarecampament.wordpress.com/portsadapters/).

## Services definition

The services used in this kata are defined in the `KataTrainApp.yaml` file. You may open it with [Postman](https://www.postman.com/) or [Swagger-Ui](https://swagger.io/tools/swagger-ui/) to test the services, after running them.

These services, and the way to run them, are further detailed in the sections below.

### Coltrain Service

The Coltrain service responds to a HTTP POST request that comes with form data telling you which train the customer wants to reserve seats on, and how many they want. It should return a json document detailing the reservation that has been made.

A reservation comprises of a json document with three fields: the train id, the booking reference, and the ids of the seats that have been reserved.

Example json:

```json
{"train_id": "express_2000", "booking_reference": "75bcd15", "seats": ["1A", "1B"]}
```

If it is not possible to find suitable seats to reserve, the service should instead return an empty list of seats and an empty string for the booking reference.

### Booking Reference Service

You can get a unique booking reference using a REST-based service. For test purposes, you can start a local service using the provided code in the "booking_reference_service" folder. You can assume the real service will behave the same way, but be available on a different url.

Install [node](https://nodejs.org/en/download/), then run:

```bash
npm install
node .
```

You can use this service to get a unique booking reference. Make a GET request to:
```bash
http://localhost:8282/booking_reference
```

This will return a string that looks a bit like this:
```bash
75bcd15
```
### Train Data Service

You can get information about which each train has by using the train data service. For test purposes, you can start a local service using the provided code in the "train_data_service" folder. You can assume the real service will behave the same way, but be available on a different url.

Again, you need [node](https://nodejs.org/en/download/), then start the server by running:

```bash
npm install
node .
```

You can use this service to get data for example about the train with id "express_2000" like this:

```bash
http://localhost:8181/data_for_train/express_2000
```

this will return a json document with information about the seats that this train has. The document you get back will look for example like this:

```bash
{"seats": {"1A": {"booking_reference": "", "seat_number": "1", "coach": "A"}, "2A": {"booking_reference": "", "seat_number": "2", "coach": "A"}}}
```

Note I've left out all the extraneous details about where the train is going to and from, at what time, whether there's a buffet car etc. All that's there is which seats the train has, and if they are already booked. A seat is available if the "booking_reference" field contains an empty string. To reserve seats on a train, you'll need to make a POST request to this url:

```bash
http://localhost:8181/reserve
```

and attach form data for which seats to reserve. There should be three fields:

```bash
"train_id", "seats", "booking_reference"
```

The "seats" field should be a json encoded list of seat ids, for example:
```bash
'["1A", "2A"]'
```

The other two fields are ordinary strings. Note the server will prevent you from booking a seat that is already reserved with another booking reference.

The service has one additional method, that will remove all reservations on a particular train. Use it with care:
```bash
http://localhost:8181/reset/express_2000
```


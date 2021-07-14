# MyStore

### mystore-api

REST API for processing requests of orders or to update/query the inventory

### mystore-ui

Not yet built, the aim is to build a frontend using Scala.js to interact with mystore-api

## Running/Testing the app

To run the application and Cassandra together via docker-compose, from the root of the project run the
following `./sbt dockerUp`.

To stop everything, run `./sbt dockerDown`.

To start everything in docker, run all the integration tests and then stop everything, run `./sbt dockerIntTests`.

To only run the integration tests (if everything is already running in docker), run `./sbt IntegrationTest/test`.

## HTTP Endpoints

### Return all orders for a user

`curl localhost:8080/orders/<userId>`

### Order an item for a user

`curl -X PUT localhost:8080/orders/<userId>/<orderId>/<quantity>`

### Delete a users' order

`curl -X DELETE localhost:8080/orders/<userId>/<orderId>`

### Return inventory information for an item

`curl localhost:8080/inventory/<itemId>`

### Add an item to the inventory

`curl -X PUT localhost:8080/inventory/<itemId>/<short_description>/<quantity>`

### Update the quantity of an item in the inventory by a given amount

`curl -X PUT localhost:8080/inventory/<increase|decrease>/<itemId>/<quantity>`

### Update the quantity of an item in the inventory to a given amount

`curl -X PUT localhost:8080/inventory/<itemId>/<quantity>`

### Remove an item from the inventory completely

`curl -X DELETE localhost:8080/inventory/<itemId>`

## Metrics

Metrics surfaced by Kamon are exposed here -> `http://localhost:9095/metrics`

# MyStore

## Running/Testing the app

To run the application and Cassandra together via docker-compose, from the root of the project run the
following `./sbt dockerUp`.

To run the integration tests (once everything is running in docker), run `./sbt IntegrationTest/test`.

To stop everything, run `./sbt dockerDown`.

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

## Cqlsh

### To access the command line (`cqlsh`) via docker

`docker exec -it cassandra_container_name_or_id cqlsh`

### To view all keyspaces

`describe keyspaces`

### To switch keyspace

`use keyspace_name;`

### To list all tables

`describe tables;`

### To select all records from a table

`select * from table_name;`

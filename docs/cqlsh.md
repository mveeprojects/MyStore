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

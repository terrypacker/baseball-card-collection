# Baseball Card Collection
Application for tracking your collectorCard collection

## Current Limitations
* Docker support has not been tested so the compose file needs properties set to work

## Technologies
* Vaadin 24 Flow and Components (React UI)
* OCI Docker Spring Boot Support
* JOOQ

## Usage

### Run postgres container for testing
```shell
docker run --name postgres-collectorCard-collection -e POSTGRES_DB=collectorCard-collection -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -p 5432:5432 -d postgres:16
```

### Build Full Application
## Prerequisites
* Docker running

## Build
Build the Vaadin UI for production and package up into image
```shell
./mvnw clean vaadin:prepare-frontend install spring-boot:build-image -Pproduction
```
### Run Docker Image
See docker-compose.yml included for reference
```shell
docker compose up -d --force-recreate
```

### Properties
The following VM Properties need to be set when starting the application, see the application.yml file.
* N/A

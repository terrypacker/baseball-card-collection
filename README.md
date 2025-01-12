# Baseball Card Collection
Application for tracking your card collection

## Current Limitations
* Docker support has not been tested so the compose file needs properties set to work

## Technologies
* Vaadin 24 Flow and Components (React UI)
* OCI Docker Spring Boot Support

## Usage

### Run postgres container for testing
```
docker run --name postgres-baseball -e POSTGRES_DB=baseball-card-collection -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -p 5432:5432 -d postgres:16
```

### Build Docker Image
Build the Vaadin UI for production and package up into image
```
./mvnw clean vaadin:prepare-frontend install spring-boot:build-image -Pproduction
```
### Run Docker Image
See docker-compose.yml included for reference
```
docker compose up -d --force-recreate
```

### Properties
The following VM Properties need to be set when starting the application, see the application.yml file.

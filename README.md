# Camel Paho Demo

## Start MQTT

```shell
docker-compose up -d
```

## Start Quarkus

### Before you start
Before you can start the application you must rename the `.env.template` file into `.env` and replace `<Password>` 
with the actual password of your mqtt broker inside the `.env` file.

### Start
```shell
./mvnw clean compile quarkus:dev
```

## Publish Something with Quarkus

Open the request.http in Intellij. There is already a request implemented. You run this Request and it will publish 
the content of the body to the topic configurated in the `application.properties` (`paho.topic-write`) file. 

## Subscribe to MQTT

Quarkus will automatically subscribe to the topic configured in the `application.properties` (`paho.topic-read`). To 
test this you need to publish something with something like the MQTT Explorer.

https://mqtt-explorer.com/

## Code Documentation

### CamelPahoCallback
This file ist for the Subscription Callback and includes the methods that will run when a message arrived, the 
connection is lost, etc.

### CamelPahoPublisher
Is the code for publishing something to the mqtt broker.

### CamelPahoSubscriber
Code for subscribing to a mqtt broker.

### PahoConfiguration
Class for the Configurations set in the application.properties or .env file. See below.

## Configurations
There are two configurations files (`.env` file, `application.properties`). The .env file is not version controlled 
and only includes the password. The other configurations are present in the application.properties.

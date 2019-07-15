# Spreadsheet Processor
Processor Project for process messages and persist a result in a database

## How it Works 
Get the message in rabbitmq localhost, does a download in a spreadsheet file located in aws s3 bucket.
Process the file, convert and persist products in a database

## Configuration
* Firstval, we need to install a commons jar (available [here](https://github.com/leonardodosanjossantos/spreadsheet-commons))
* Java 8 or above
* Maven
* RabbitMq for messages
* Mysql Client

## How to Run
```bash
mvn spring-boot:run
```

## Main Frameworks

* Spring Boot
* Lombook

## Test Frameworks 

* Junit
* Mockito
* Matcher

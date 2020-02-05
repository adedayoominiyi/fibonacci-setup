# fibonnaci-setup
A Micronaut/GRPC Fibonacci Server

> This project contains a Fibonacci GRPC Server and a Proxy Server RESTful endpoint.

## Table of Contents

- [RESTful API](#restful-api)
- [Technical Specs](#technical-specs)
- [Project Structure](#project-structure)
- [Cloning the Repo](#cloning-the-repo)
- [Running the Service](#running-the-service)

## RESTful API
```
GET /fibonacci/<maxNumber>
```
Where &lt;maxNumber&gt; must be greater than **0** and less than or equal to **2147483647** 

## Technical Specs
- Java 8
- Apache Maven 3.6.3 
- Micronaut Framework 1.2.10 (https://micronaut.io/download.html)
- gRPC 1.26.0 (https://www.grpc.io/)
- Docker/Docker Compose
- IntelliJ IDEA 2019.3.1 (Community Edition)

## Project Structure
The project consists of 2 directories/folders namely **fibonacci-server** and **proxy-service**.

- **fibonacci-server** contains the Fibonacci RPC server based on gRPC. It runs on port 9091.
- **proxy-service** contains the Proxy Service RESTful endpoint based on the Micronaut Framework. It runs on port 9090.

## Cloning the Repo
Clone the project using 
```sh
  git clone https://github.com/adedayoominiyi/fibonacci-setup.git
```

## Running the Service
- If you have docker on your machine, the quickest way to run the service is via Docker Compose from the root directory. Note this takes a little while as the source code gets built and packaged via a MultiStage build:
	```sh
	fibonacci-setup$ docker-compose up -d
	```
	When the build is done, then you can open your browser and navigate to for example, http://localhost:9090/fibonacci/17. **Note** You may need to change localhost to the IP address of your Docker setup for example, if you are using Docker Toolkit.
	To shut it down:
	```sh
	fibonacci-setup$ docker-compose down
	```
 
- If you would rather run the code directly, then you need **Java 8** and **Maven 3**. **Note** You need to build and run **fibonacci-server** before 
  building and running **proxy-service**
  
  To build **fibonacci-server**
  ```sh
  fibonacci-setup$ cd fibonacci-server
  fibonacci-server$ mvn clean package
  fibonacci-server$ java -jar target/fibonacci-server-1.0-SNAPSHOT-jar-with-dependencies.jar
  ```
  
  To build **proxy-service**
  ```sh
  fibonacci-setup$ cd proxy-service
  proxy-service$ mvn clean package  (This runs the test as well)
  proxy-service$ java -jar target/proxy-service-0.1.jar
  ```
  Then you can open your browser and navigate to for example, http://localhost:9090/fibonacci/23

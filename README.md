# MOC-ORDERS
API where users can create and manage orders

## Prerequisites
- Java 8
- Maven
- Git
- Docker
- Docker Compose

## How to execute

1) Clone this project in your computer
2) In the project's root directory, open your terminal and let's start the postgresql database:
    ```
    docker-compose up
    ```
3) Build the project:
    ```
    mvn clean install
    ```
4) Execute the project:
    ```
    java -jar {root-directory}/target/moc-orders-0.0.1-SNAPSHOT.jar
    ```

## *** All APIs calls are in the file moc-orders.postman_collection.json - Import this file in your Postman application
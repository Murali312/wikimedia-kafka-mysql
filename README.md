# wikimedia-kafka-mysql
- Project Overview : 
    A real-time data streaming pipeline that captures live Wikimedia recent changes events, publishes them to Apache Kafka, consumes the events, and persists them into a MySQL database for storage and further analysis.

- Architecture :
    Wikimedia Event Stream  →  Kafka Producer  →  Kafka Broker  →  Kafka Consumer  →  MySQL Database

  - Producer: Connects to Wikimedia’s real-time API and streams data into Kafka.
  - Kafka Broker: Acts as the message queue for storing and forwarding events.
  - Consumer: Subscribes to Kafka topics, consumes events, and saves them in MySQL.
  - MySQL: Stores event data for querying, analytics, or further processing.

- Tech Stack :
  - Java (Spring Boot)
  - Apache Kafka
  - MySQL Database
  - EventSource (Server-Sent Events API)
  - Maven

Getting Started 

1. Prerequisites
  - Java 17+
  - Apache Kafka
  - MySQL
  - Maven


2. Clone the Repository
   git clone https://github.com/<your-username>/wikimedia-kafka-mysql-streaming.git
   cd wikimedia-kafka-mysql-streaming

3. Start Kafka & Zookeeper
   # Start Zookeeper
   bin/zookeeper-server-start.sh config/zookeeper.properties

   # Start Kafka broker
   bin/kafka-server-start.sh config/server.properties

4. Setup MySQL Database
    CREATE DATABASE wikimedia;

  Update your application.properties of 'kafka-consumer-database' module with MySQL credentials:
    spring.datasource.url=jdbc:mysql://localhost:3306/wikimedia
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update

5. Run the Application
     mvn spring-boot:run


- Sample Data Stored in MySQL
    ID	Wikimedia Data (JSON)
    1	  {"id":123,"title":"Main Page","timestamp":"2025-08-29T10:15:30Z","user":"ExampleUser"}

- Use Cases
    - Real-time monitoring of Wikimedia edits
    - Building streaming ETL pipelines
    - Demonstrating Kafka integration with Spring Boot and MySQL



# Trip Agency Application #
It is a Spring Boot application that allows booking a trip for a specific user. An user can register and then he can log in in the application. A list of available trips is presented, and the user can book a trip from the collection. A user can make a booking only by being logged in into the system.

The backend is represented by a couple of Spring Boot microservices, each responsible for user management, trip management and also authentication. They share a common Postgres database. The application uses Eureka Server and Zuul Gateway for service discovery and load balancing. 

The frontend is implemented using React. It is formed by two micro frontends, one (app1) for managing the trips, and another one (app2) that allows the visualisation of the most popular destination. The integration of app2 in app1 was done using yarn, lerna, babel and webpack. The app2 retrieves the most popular destination by calling a firebase function (deployed in Google Cloud).

For more details, check the Doc.

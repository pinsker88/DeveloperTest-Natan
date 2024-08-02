# Getting Started

To run the project you need apply one of the actions below (1 or 2):
1. you just open it in Intellij Idea and run the main
2. go to terminal -> open the project in terminal -> run ./mvn spring-boot:run

When the application is running you can get the result by postman
Port 8080
According to requirements there is one unsecured endpoint : /routing/{origin}/{destination}

Example:
http://localhost:8080/routing/ISR/UZB for successful response
http://localhost:8080/routing/GBR/UZB for 400 response

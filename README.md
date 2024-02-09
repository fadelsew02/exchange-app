# exchange-app




Exchange app is a web application that requires Docker for installation.


## Prerequisites to compile the project (Really mandatory)

- [Java](https://www.java.com/) installed on your machine (Take the time to install a java version with a higher recipe than 17 so that will allow you to compile the project but 17 will also work ).
- [Maven](https://maven.apache.org/) for dependency management.

## Technology Stack

The application is built using the following technologies:

- Backend: java ,spring-boot
- Frontend: HTML , CSS , thymeleaf
- Database: PostgreSQL
- Mail Server:smtp4dev
- Containerization: Docker, Docker Compose

## Functionalities

- User registration and login
  - Users can register by providing their email, password, phone number, First name, Last name.
  - An email is sent to the user's email address with token.
  - After copy the token, the user can paste it in the field allow and will be send to the loging page.
  - Users can log in using their email and password.

- Currency exchange with real-time exchange rates
  - Users can select the amount they want to exchange and the currency they want to exchange from and to , the emai's of destination and the payment method
  - Users currency can be add in the menu 'choisir la devise'.
  - The application calculates the exchange rate and displays the amount to be received by using (https://freecurrencyapi.com/docs/currencies/).
  - Users can confirm the exchange and the transaction is recorded in the database.

- View transaction history
  - Users can view their transaction history with details such as the exchange rate, amount, and date in the menu 'mes transactions'.

- Secure communication using JWT tokens
  - The application uses JWT tokens to securely communicate between the client and the server.
  - When a user logs in, a JWT token is generated and sent to the client.
  - The token is included in all subsequent requests to the server to authenticate the user.

- Add of payment method (account,MOMO,credit card)
  - Users can specify they payment method in the menu 'mon compte'.

## Installation

1. Install Docker on your system. You can find the installation instructions for your specific operating system on the [official Docker website](https://docs.docker.com/get-docker/).

2. Start the Docker containers using Docker Compose on the resources file of the application in the terminal
        docker-compose up -d

3.  Build the Docker containers:       
        docker-compose up --build

4. Usage         

     Run the project using mvn spring-boot:run

     Open the project in your browser at http://localhost:8080.

     Open the mail server on port http://localhost:5435 to receive the token for login or sign up.

## Additional information

- Check the application.properties file to configure settings like database, port, etc.
Each time you add a new dependency you must run: "mvn install"
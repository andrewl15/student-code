# Favorite Movies Server

> This application was built for Java 21. Before running the application, verify that your project is using a version 21 SDK. In IntelliJ go to **File > Project Structure** and in the window that appears check the **SDK** field.

The Favorite Movies application consists of several parts:
- a SQL database
- a server with REST APIs for Authentication, Users, Movies, and Likes
- a customer facing, React web application client

## Accounts

The `database/FavoriteMoviesDB.sql` script contains several accounts that you can login with. The hashed passwords that you can see in the script are all the word `password`. Here are two accounts from the script as a quick reference:

* `movie_fan`
* `cameron`

## Setup

### Step One

Before running the application, create the `FavoriteMoviesDB` database in PGAdmin. Then open a query tool for `FavoriteMoviesDB` and open/run the `database/FavoriteMoviesDB.sql` script to create the tables with some test users and data. **The database name is case sensitive. Ensure the database name is precisely `FavoriteMoviesDB`.**

### Step Two

To run the application, first open the `favorite-movies-server` project in IntelliJ and run the `main()` method of the `Application` class. Once the server is running, you can interact with it from the React web application.

> If when running the server you have issues with the port already being in use, you can use the command `npx kill-port 9000` in your terminal to stop any process using the port this server requires.

> You can also run the application via command-line. Run the `./mvnw spring-boot:run` command while working from the `favorite-movies-server` folder in your terminal. This is a "lighter" way to run the application without IntelliJ. You can stop it at any time with `Control+C`.

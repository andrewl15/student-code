# Bookmark Manager Server

> This application was built for Java 21. Before running the application, verify that your project is using a version 21 SDK. In IntelliJ go to **File > Project Structure** and in the window that appears check the **SDK** field.

This application demonstrates concepts learned in Module 2 (Client-Server Programming in Java). It serves as the backend for the React lecture projects.

The Bookmark Manager application consists of several parts:
- a SQL database
- a server with REST APIs for Authentication, Users, Bookmarks, and Tags
- a customer facing, React web application client

Throughout the application, comments at the top of each class and interface explain its purpose.

## Accounts

The `database/BookmarkDB.sql` script contains several accounts that you can login with. The hashed passwords that you can see in the script are all the word `password`. Here are two accounts from the script as a quick reference:

* `job_coach`
* `newbie_coder`

## Setup

### Step One

Before running the application, create the `BookmarkDB` database in PGAdmin. Then open a query tool for `BookmarkDB` and open/run the `database/BookmarkDB.sql` script to create the tables with some test users and data. **The database name is case sensitive. Ensure the database name is precisely `BookmarkDB`.**

### Step Two

To run the application, first open the `bookmark-server` project in IntelliJ and run the `main()` method of the `BookmarkManagerApplication` class. Once the server is running, you can interact with it from the React web application.

> If when running the server you have issues with the port already being in use, you can use the command `npx kill-port 9000` in your terminal to stop any process using the port this server requires.

> You can also run the application via command-line. Run the `./mvnw spring-boot:run` command while working from the `bookmark-server` folder in your terminal. This is a "lighter" way to run the application without IntelliJ. You can stop it at any time with `Control+C`.

## Optional: Test the Endpoints

You can test the endpoints of the server REST API directly using Postman. In the `bookmark-server/postman` folder, there is a `Bookmark Manager.postman_collection.json` file that you can import into Postman which creates a Collection that you can use for testing. This collection automatically saves the user token after login to use when making other requests.

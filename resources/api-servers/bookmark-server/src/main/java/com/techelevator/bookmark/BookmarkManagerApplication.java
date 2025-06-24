package com.techelevator.bookmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * BookmarkManagerApplication is the class that starts running the Spring framework and launch the
 * web application server which listens for HTTP requests from clients.
 */
@SpringBootApplication
public class BookmarkManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookmarkManagerApplication.class, args);
    }

}

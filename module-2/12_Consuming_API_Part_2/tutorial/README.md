# Web Services POST, PUT, DELETE Tutorial (Java)

In this tutorial, you'll extend the meetup locations example from the first day by adding features to:

* Add a new location (`POST`)
* Modify an existing location (`PUT`)
* Remove a location (`DELETE`)
* Capture and handle HTTP exceptions

When complete, this produces a full **C**reate **R**ead **U**pdate **D**elete (CRUD) console Web client application.

## Step One: Start the server

Before you start, make sure that the web API is up and running. First, change directories into the `./server/` folder.

Next, run the command `npm install` to install any dependencies. You won't need to do this on any subsequent run.

While still in the command line, run the command `npm start` to start the json-server application. If there aren't any errors, you'll see the following, which means that you've successfully set up your web API:

```shell
  \{^_^}/ hi!

  Loading data-generation.js
  Done

  Resources
  http://127.0.0.1:3000/locations

  Home
  http://127.0.0.1:3000

  Type s + enter at any time to create a snapshot of the database
```

> Note that the name `localhost` resolves to `127.0.0.1`, so when you see that IP address in the message, it means that json-server is ready to respond to requests on `localhost`.

When json-server is running on port 3000, no other applications—including other copies of json-server—are able to use port 3000. To free up the port, be sure to stop json-server when you're finished with this tutorial. You do that by selecting the terminal where you typed `npm start` and pressing `Ctrl+C`. Or if you've already closed that terminal, open a new terminal and type:

```
taskkill -T -F -IM node.exe
```

In this tutorial, you'll modify data on the server. As you're working, you may come across a situation where you want to reset the data. To do this, first stop the server with `Ctrl+C`, then restart it with `npm start`.

## Step Two: Review the starting code

### Application structure

The `src/main/java/com/` folder:

- techelevator
  - locations
    - App.java `<-- main application driver class`
    - model
      - Location.java `<-- Location data model class`
    - services
      - ConsoleService.java `<-- Console input and output service`
      - LocationService.java `<-- REST client and Web API access performed here`
  - util
    - BasicLogger.java `<-- Error logging class`
    - BasicLoggerExeception.java `<-- Logger exception class`

### Provided code versus your code

Everything but part of the `LocationService` class is provided for you. You'll complete the `add()`, `update()`, and `delete()` methods in that class. The `getAll()` and `getOne()` methods are based on the work you did in a previous tutorial.

The methods in the `App` class use the `ConsoleService` class to prompt and retrieve input from the user and use the `LocationService` class to request and retrieve the data from the API.

## Step Three: Add a location with POST

Open the `LocationService.java` file and find the `add()` method:

```java
public Location add(Location newLocation) {
    // Step Three: Add a location with POST
    return null;
}
```

The code that calls this function passes in a newly created `Location` object. This method sends the data for that location to the API.

First, you'll use the method named `contentType()`. The purpose of this method is to add a header to the POST request. This lets the server know the _Content Type_ contained in the request. Here, that type is set to `MediaType.APPLICATION_JSON`.

To use this method, you'll add it after the `post()` method:

```java
restClient.post()
    .contentType(MediaType.APPLICATION_JSON)
```

Next, you'll use the method named `body()`. The purpose of this method is to add a body to the POST request. In this case, the body of the request is the `newLocation` parameter. The `body()` method is usually placed after the `contentType()` method:

```java
restClient.post()
    .contentType(MediaType.APPLICATION_JSON)
    .body(newLocation)
```

> **NOTE:** the order of the `contentType()` and `body()` methods don't matter as long as they're after the `post()` method and before the `retrieve` method.

Now that the POST request is constructed, you're ready to send it to the server and get the response.

The `retrieve()` method sends the requests and prepares the response. You can use the `body()` method after the `retrieve()` method to get the body of the response similar to a response from a GET request.

Putting `Location.class` in the parenthesis of `body()` deserializes the response body into a Location object. You can save the returned `Location` object into a variable called `returnedLocation` by assigning to the method call:

```java
Location returnedLocation = null;
returnedLocation = restClient.post()
                    .contentType(APPLICATION_JSON)
                    .body(newLocation)
                    .retrieve()
                    .body(Location.class);

return null;
```

Note that the difference between `returnedLocation` and `newLocation` is that `returnedLocation` has the id that was assigned by the API when it added it to the datastore.

Next, remove the `return null` statement at the end of the method, if still present. You only needed it to satisfy the Java compiler when you first opened the tutorial project.

The `add()` method looks like this:

```java
public Location add(Location newLocation) {

    Location returnedLocation = null;
    returnedLocation = restClient.post()
            .contentType(APPLICATION_JSON)
            .body(newLocation)
            .retrieve()
            .body(Location.class);

    return returnedLocation;
}
```

## Step Four: Modify a location with PUT

You'll modify the `update()` method next. This method is invoked similarly to the `add()` method. It's passed an existing location modified by the user rather than a brand new location, and it returns a `boolean` value indicating if it was successful or not. There's no need for it to return an object, since the returned object would be identical to the one that's passed in.

This code is similar to the code you added in the `add()` method. The difference is you'll use the `put()` method instead of `post()`. The URL for the `PUT` is `API_BASE_URL` with a forward slash (`/`) and the `id`appended. Since `RestClient` was instantiated with `API_BASE_URL` only the slash and id are needed. You can get the id from the `Location` object, `updatedLocation.getId()`:

```java
restClient.put()
    .uri("/" + updatedLocation.getId())
    .contentType(APPLICATION_JSON)
    .body(updatedLocation)
    .retrieve()
```

The `put()` method doesn't return a body in the response so add `toBodilessEntity()` after the `retrieve` method to process the response. For now, change the `return false` statement at the end of the method to `return true`. You'll do more with the return value in Step Seven:

```java
public boolean update(Location updatedLocation) {

    restClient.put()
            .uri("/" + updatedLocation.getId())
            .contentType(APPLICATION_JSON)
            .body(updatedLocation)
            .retrieve()
            .toBodilessEntity();

    return true;
}
```
## Step Five: Delete a location with DELETE

To delete a location, you only need to send the `id` of the location to delete. Similar to the `put()` method, the `uri()` method can be used. Add this code to the `delete()` method:

```java
restClient.delete()
    .uri("/" + id)
    .retrieve()
    .toBodilessEntity();
```

Next, change the `return false` statement at the end of the method to `return true`. You'll do more with the return value in Step Seven:

```java
public boolean delete(int id) {

    restClient.delete()
            .uri("/" + id)
            .retrieve()
            .toBodilessEntity();

    return true;
}
```

## Step Six: Test your application

Run the application and execute each menu item. If you followed the instructions, the application works as expected. If you encounter any issues, go back and review the previous steps.

From the main menu, select option 5. When prompted to select a location, enter an invalid number like 999 and observe the result. The program stops because of an error that was returned from the server.

## Step Seven: Add exception handling for HTTP errors

Next you'll capture and log the errors sent back from the server and prevent the application from crashing. To do this, you'll use `try/catch` blocks. `RestClient` throws a `RestClientResponseException` when an error response code is received or a `ResourceAccessException` when no response is received at all. You'll `catch` those exceptions and log them by calling `BasicLogger.log()`.

Inside the `catch` block, you'll use the exception methods `getStatusCode().value()`, `getStatusText()`, and `getMessage()` to get more detailed information about what happened, and include it in the string sent to the log. You'll also make sure the method returns a `null` or `false` value, to communicate the failure to the caller. The `try/catch` block pattern looks like this:

```java
try {
    // Call to RestClient goes here
} catch (RestClientResponseException e) {
    BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
} catch (ResourceAccessException e) {
    BasicLogger.log(e.getMessage());
}
```

You can see an example of this exception handling in the `getAll()` method of `LocationService`:

```java
public Location[] getAll() {
    Location[] locations = null;
    try {
        locations = restClient.get()
                .retrieve()
                .body(Location[].class);

    } catch (RestClientResponseException e) {
        BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
    } catch (ResourceAccessException e) {
        BasicLogger.log(e.getMessage());
    }
    return locations;
}
```

Apply the same exception handling pattern to the `add()` method you wrote:

```java
public Location add(Location newLocation) {

    Location returnedLocation = null;
    try {
        returnedLocation = restClient.post()
                .contentType(APPLICATION_JSON)
                .body(newLocation)
                .retrieve()
                .body(Location.class);

    } catch (RestClientResponseException e) {
        BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
    } catch (ResourceAccessException e) {
        BasicLogger.log(e.getMessage());
    }
    return returnedLocation;
}
```

Next, apply it to the `update()` method:

```java
public boolean update(Location updatedLocation) {

    boolean success = false;
    try {
        restClient.put()
                .uri("/" + updatedLocation.getId())
                .contentType(APPLICATION_JSON)
                .body(updatedLocation)
                .retrieve()
                .toBodilessEntity();

        success = true;
    } catch (RestClientResponseException e) {
        BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
    } catch (ResourceAccessException e) {
        BasicLogger.log(e.getMessage());
    }
    return success;
}
```

Note that in this case, a new `boolean` variable called `success` was also added. It's initially set to `false`, and only changed to `true` after the call to `restClient.put()`. If an exception is thrown, it never gets changed to `true`, so the method returns `false`, indicating failure.

Finally, apply the same exception handling pattern to the `delete()` method:

```java
public boolean delete(int id) {

    boolean success = false;
    try {
        restClient.delete()
                .uri("/" + id)
                .retrieve()
                .toBodilessEntity();

        success = true;
    } catch (RestClientResponseException e) {
        BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
    } catch (ResourceAccessException e) {
        BasicLogger.log(e.getMessage());
    }
    return success;
}
```

After making those changes, rerun the program, select menu option 5, and enter 999. You'll receive a brief error message, and see a file containing more details in the `logs` folder. The application continues to run.


## Summary

In this tutorial, you learned how to:

* Use the HTTP `POST` Web API call to add a new Location
* Use the HTTP `PUT` Web API call to modify a new Location
* Use the HTTP `DELETE` Web API call to delete a new Location
* Use exception handling for HTTP errors

### Don't forget to stop json-server

When you're done with the tutorial, remember to stop json-server. Directions are under Step One.

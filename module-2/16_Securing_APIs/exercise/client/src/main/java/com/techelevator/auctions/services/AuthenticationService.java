package com.techelevator.auctions.services;

import com.techelevator.auctions.model.CredentialsDto;
import com.techelevator.auctions.model.TokenDto;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

public class AuthenticationService {

    private static final String API_BASE_URL = "http://localhost:8080";
    private final RestClient restClient = RestClient.create(API_BASE_URL);

    public String login(String username, String password) {

        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setUsername(username);
        credentialsDto.setPassword(password);

        String token = null;
        try {
            TokenDto tokenDto = restClient.post()
                    .uri("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(credentialsDto)
                    .retrieve()
                    .body(TokenDto.class);
            // TODO: Add code below to send the request to the API
            //  and get the token from the response.
            if(tokenDto != null){
                token = tokenDto.getToken();
            }

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return token;
    }

}

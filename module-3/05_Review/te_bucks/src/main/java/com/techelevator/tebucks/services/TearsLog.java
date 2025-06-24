package com.techelevator.tebucks.services;

import com.techelevator.tebucks.model.LoginDto;
import com.techelevator.tebucks.model.LoginResponseDto;
import com.techelevator.tebucks.model.TxLog;
import com.techelevator.tebucks.model.TxLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class TearsLog implements ExternalLog{
    private final String API_URL = "https://tears.azurewebsites.net";
    private static RestClient restClient;
    private static RestTemplate restTemplate;
    private LoginDto tearsLoginDto = new LoginDto();
    private String token;
    // insert values from application.properties
    public TearsLog(@Value("${tears.username}") String username,@Value("${tears.password}") String password){
        tearsLoginDto.setUsername(username);
        tearsLoginDto.setPassword(password);
        // if the static variable is null, instantiate the RestTemplate
        if(restClient == null){
            restClient = RestClient.create(API_URL);
        }
        // log in and get a fresh token
        token = getTEARSToken();
    }
    @Override
    public TxLog logItem(TxLogDTO dataIn) {
        try{
            TxLog log = restClient.post()
                    .uri("/api/txlog")
                    .header("Authorization","Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dataIn)
                    .retrieve()
                    .body(TxLog.class);
            return log;
        } catch(ResourceAccessException ex){
            // if we are here, we can't get to the server, noting to do
        }catch(RestClientResponseException ex){
            // if we got an exception, check for 401 and refresh the token
            if(ex.getRawStatusCode() == 401){
                token = getTEARSToken();
            }

        }


        return null;
    }

    private String getTEARSToken(){
        try{
            LoginResponseDto tearsUser = restClient.post()
                    .uri("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(tearsLoginDto)
                    .retrieve()
                    .body(LoginResponseDto.class);
            return tearsUser.getToken();
        } catch (Exception e){
            return null;
        }
    }
}


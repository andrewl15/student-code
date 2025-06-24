package com.techelevator.auctions.services;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {

    private static final String API_BASE_URL = "http://localhost:8080";
    private static final String EXPECTED_API_URL = API_BASE_URL + "/login";

    private final AuthenticationService sut = new AuthenticationService();

    @Test
    public void step2_loginMethod() {
        String testToken = "abcdefgh123456789";

        RestClient restClient = RestClient.builder()
                .baseUrl(API_BASE_URL)
                .requestInterceptor(new ClientHttpRequestInterceptor() {
                    @Override
                    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

                        String url = request.getURI().toString();
                        HttpMethod method = request.getMethod();
                        MediaType contentTypeHeader = request.getHeaders().getContentType();

                        String actualBody = new String(body).replace(" ", "");
                        String expectedBody = "{\"username\":\"testuser\", \"password\":\"testpass\"}".replace(" ", "");

                        boolean isMatchingUrl = url != null && url.equals(EXPECTED_API_URL);
                        boolean isMatchingMethod = method.equals(HttpMethod.POST);
                        boolean isMatchingHeaders = contentTypeHeader != null && contentTypeHeader.equals(MediaType.APPLICATION_JSON);
                        boolean isMatchingBody = expectedBody.equals(actualBody);

                        boolean isMatchingRequest = isMatchingUrl &&
                                isMatchingMethod &&
                                isMatchingHeaders &&
                                isMatchingBody;

                        if (!isMatchingRequest) {
                            fail("Didn't send the expected request to login. Verify that the URL, HTTP method, and headers are correct.");
                        }

                        ClientHttpResponse mockResponse = new ClientHttpResponse() {
                            @Override
                            public HttpStatusCode getStatusCode() {
                                return HttpStatus.OK;
                            }

                            @Override
                            public String getStatusText() {
                                return "OK";
                            }

                            @Override
                            public void close() {
                                // Not needed for mock response
                            }

                            @Override
                            public InputStream getBody() {
                                String jsonResponse = "{ \"token\" : \"" + testToken + "\"}";
                                return new ByteArrayInputStream(jsonResponse.getBytes());
                            }

                            @Override
                            public HttpHeaders getHeaders() {
                                HttpHeaders responseHeaders = new HttpHeaders();
                                responseHeaders.setContentType(MediaType.APPLICATION_JSON);
                                return responseHeaders;
                            }
                        };

                        return mockResponse;
                    }
                })
                .build();

        ReflectionTestUtils.setField(sut, "restClient", restClient);
        String token = sut.login("testuser", "testpass");

        assertNotNull(token, "Didn't get anything back after sending username and password.");
        assertEquals(testToken, token, "Didn't get expected token after logging in.");
    }

}

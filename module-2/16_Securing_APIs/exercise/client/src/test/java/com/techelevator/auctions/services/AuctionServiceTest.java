package com.techelevator.auctions.services;

import com.techelevator.auctions.model.Auction;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuctionServiceTest {
    
    private static final int TEST_ID = 99;
    private static final String API_BASE_URL = "http://localhost:8080/auctions";
    private static final String EXPECTED_API_URL = API_BASE_URL + "/" + TEST_ID;

    private final AuctionService sut = new AuctionService();

    @Test
    public void step3_getAuction() {

        RestClient restClient = RestClient.builder()
                .baseUrl(API_BASE_URL)
                .requestInterceptor(new ClientHttpRequestInterceptor() {
                    @Override
                    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {

                        String url = request.getURI().toString();
                        HttpMethod method = request.getMethod();
                        List<String> authHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);

                        boolean isMatchingUrl = url.equals(EXPECTED_API_URL);
                        boolean isMatchingMethod = method == HttpMethod.GET;
                        boolean isMatchingHeaders = authHeaders != null && authHeaders.size() == 1 && authHeaders.getFirst().startsWith("Bearer");
                        boolean isMatchingBody = body.length == 0;

                        boolean isMatchingRequest = isMatchingUrl &&
                                isMatchingMethod &&
                                isMatchingHeaders &&
                                isMatchingBody;

                        if (!isMatchingRequest) {
                            fail("Didn't send the expected request to retrieve all auctions. Verify that the URL, HTTP method, and headers are correct.");
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
                                String jsonResponse = "{\"id\":" + TEST_ID + "}";
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
        Auction auction = sut.getAuction(TEST_ID);

        assertNotNull(auction, "Call to getAuction() returned null.");
        assertEquals(TEST_ID, auction.getId(), "Call to getAuction() didn't return the expected data.");
    }

}

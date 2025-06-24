package com.techelevator.reservations.model;

import com.fasterxml.jackson.annotation.JsonProperty;
/*
    The acronym DTO is being used for "data transfer object". It means that this type of class is specifically
    created to transfer data between the client and the server. For example, LoginDto represents the data a client must
    pass to the server for a login endpoint, and LoginResponseDto represents the object that's returned from the server
    to the client from a login endpoint.
 */
public class LoginResponseDto {

	private String token;
	private User user;

	public LoginResponseDto(String token, User user) {
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

package com.techelevator.services;

import org.springframework.stereotype.Component;

import com.techelevator.model.CatFact;
import org.springframework.web.client.RestClient;

@Component
public class RestCatFactService implements CatFactService {
	private final String factUri = "https://teapi.netlify.app/api/cats/facts/random";
	private RestClient restClient = RestClient.create(factUri);
	@Override
	public CatFact getFact() {
		CatFact output = new CatFact();
		try{
			output = restClient.get()
					.retrieve()
					.body(CatFact.class);
		} catch(Exception e){

		}
		return output;
	}

}

package com.techelevator.services;

import org.springframework.stereotype.Component;

import com.techelevator.model.CatFact;
import org.springframework.web.client.RestClient;

@Component
public class RestCatFactService implements CatFactService {

	private final String factURI = "https://teapi.netlify.app/api/cats/facts/random";
	private RestClient restClient = RestClient.create(factURI);

	@Override
	public CatFact getFact() {
		CatFact output = new CatFact();
		try{
			// talk to the fact api
			// deserial the return
			// add it to the catfact object
			output = restClient.get()
					.retrieve()
					.body(CatFact.class);
		} catch(Exception e){
			// TODO Might want to make this more sophisticated
		}

		// return the catfact object
		return output;
	}

}

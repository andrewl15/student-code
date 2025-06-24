package com.techelevator.services;

import org.springframework.stereotype.Component;

import com.techelevator.model.CatPic;
import org.springframework.web.client.RestClient;

@Component
public class RestCatPicService implements CatPicService {
	private final String factUri = "https://teapi.netlify.app/api/cats/pictures/random";
	private RestClient restClient = RestClient.create(factUri);
	@Override
	public CatPic getPic() {
		CatPic output = new CatPic();
		try{
			output = restClient.get()
					.retrieve()
					.body(CatPic.class);
		} catch(Exception e){

		}
		return output;
	}

}	

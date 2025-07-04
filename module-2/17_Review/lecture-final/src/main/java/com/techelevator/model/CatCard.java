package com.techelevator.model;

import jakarta.validation.constraints.NotEmpty;

public class CatCard {

	private int catCardId;
	@NotEmpty
	private String catFact;
	@NotEmpty
	private String imgUrl;
	@NotEmpty
	private String caption;

	public int getCatCardId() {
		return catCardId;
	}
	public void setCatCardId(int catCardId) {
		this.catCardId = catCardId;
	}
	
	public String getCatFact() {
		return catFact;
	}
	public void setCatFact(String catFact) {
		this.catFact = catFact;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
}

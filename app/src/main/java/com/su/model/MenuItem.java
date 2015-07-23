package com.su.model;

public class MenuItem {
	int ImgId;
	String text;
	
	
	
	
	public MenuItem(int imgId, String text) {
		super();
		ImgId = imgId;
		this.text = text;
	}
	
	
	public int getImgId() {
		return ImgId;
	}
	public void setImgId(int imgId) {
		ImgId = imgId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}

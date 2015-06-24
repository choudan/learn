package com.download.doctorback;

import java.util.ArrayList;

public class ItemEntity {

    private ArrayList<String> imageUrls; // 九宫格图片的URL集合  
    private String date,userWords,doc_time,docXuewei,docBack;
    private int type;

	public ItemEntity(String date, String userWords,String doc_time,String docXuewei,String docBack,int type, ArrayList<String> imageUrls) {
		super();
		this.date = date;
		this.userWords = userWords;
		this.imageUrls = imageUrls;
		this.docXuewei = docXuewei;
		this.docBack = docBack;
		this.type = type;
		this.doc_time=doc_time;
	}

	public ItemEntity(){
		
	}

	public ArrayList<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(ArrayList<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	 
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserWords() {
		return userWords;
	}

	public void setUserWords(String userWords) {
		this.userWords = userWords;
	}

	public String getDoc_time() {
		return doc_time;
	}

	public void setDoc_time(String doc_time) {
		this.doc_time = doc_time;
	}

	public String getDocXuewei() {
		return docXuewei;
	}

	public void setDocXuewei(String docXuewei) {
		this.docXuewei = docXuewei;
	}

	public String getDocBack() {
		return docBack;
	}

	public void setDocBack(String docBack) {
		this.docBack = docBack;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}

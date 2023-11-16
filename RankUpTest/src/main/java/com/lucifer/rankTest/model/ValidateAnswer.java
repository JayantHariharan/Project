package com.lucifer.rankTest.model;

public class ValidateAnswer {
	
	private int id;
	private String userAnswer;
	
	public ValidateAnswer(int id, String userAnswer) {
		super();
		this.id = id;
		this.userAnswer = userAnswer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	
	
	

}

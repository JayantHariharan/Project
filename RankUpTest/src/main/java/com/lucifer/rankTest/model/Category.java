package com.lucifer.rankTest.model;

import java.util.List;

public class Category {

	private String title;
	private List<QuestionsWrapper> questions;
	
	public Category(String title, List<QuestionsWrapper> questions) {
        this.title = title;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionsWrapper> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionsWrapper> questions) {
        this.questions = questions;
    }
}

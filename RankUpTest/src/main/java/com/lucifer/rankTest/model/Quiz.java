package com.lucifer.rankTest.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToMany
	private List<Questions> javaQuestions;
	@ManyToMany
	private List<Questions>cQuestions;
	@ManyToMany
	private List<Questions>pythonQuestions;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Questions> getJavaQuestions() {
		return javaQuestions;
	}
	public void setJavaQuestions(List<Questions> javaQuestions) {
		this.javaQuestions = javaQuestions;
	}
	public List<Questions> getcQuestions() {
		return cQuestions;
	}
	public void setcQuestions(List<Questions> cQuestions) {
		this.cQuestions = cQuestions;
	}
	public List<Questions> getPythonQuestions() {
		return pythonQuestions;
	}
	public void setPythonQuestions(List<Questions> pythonQuestions) {
		this.pythonQuestions = pythonQuestions;
	}
	
	
	
}

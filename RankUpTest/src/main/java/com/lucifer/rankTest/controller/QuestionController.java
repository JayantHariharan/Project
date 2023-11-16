package com.lucifer.rankTest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucifer.rankTest.model.Questions;
import com.lucifer.rankTest.service.QuestionService;

@RestController
@RequestMapping("rankUp")
public class QuestionController {

	@Autowired
	QuestionService questionService;
	
	@GetMapping("allQuestions")
	public ResponseEntity<List<Questions>> getAllQuestion() {
		return questionService.getAllQuestion();
	}
	
	@GetMapping("category/{category}")
	public ResponseEntity<List<Questions>> getQuestionsByCategory(@PathVariable String category){
		return questionService.getQuestionsByCategory(category);
	}
	
	@PostMapping("addQuestion")
	public ResponseEntity<String> addQuestion(@RequestBody Questions question) {
		return questionService.addQuestion(question);
	}
	
	@PutMapping("addQuestion/{id}")
	public ResponseEntity<String> updateQuestion(@PathVariable int id,@ModelAttribute Questions question) {
		return questionService.updateQuestion(id,question);
	}
	
	@DeleteMapping("deleteQuestion/{id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable int id){
		return questionService.deleteQuestion(id);
	}
}

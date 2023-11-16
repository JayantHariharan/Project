package com.lucifer.rankTest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucifer.rankTest.dao.QuestionDao;
import com.lucifer.rankTest.model.Questions;

@Service
public class QuestionService {
	
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<List<Questions>> getAllQuestion() {
		// TODO Auto-generated method stub
		try {
			return  new ResponseEntity<>(questionDao.findAll(),HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
		
	}

	public ResponseEntity<List<Questions>> getQuestionsByCategory(String category) {
		// TODO Auto-generated method stub
		try {
			return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> addQuestion(Questions question) {
		// TODO Auto-generated method stub
		
		try {
			questionDao.save(question);
			return new ResponseEntity<>("success",HttpStatus.CREATED);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<>("failed",HttpStatus.BAD_REQUEST);
		
	}

	public ResponseEntity<String> updateQuestion(int id, Questions updatedQuestion) {
		// TODO Auto-generated method stub
		  
         try {
        	 Questions existQuestion=questionDao.findById(id).orElse(null);
    		 existQuestion.setCategory(updatedQuestion.getCategory());
             existQuestion.setQuestion(updatedQuestion.getQuestion());
             existQuestion.setOption1(updatedQuestion.getOption1());
             existQuestion.setOption2(updatedQuestion.getOption2());
             existQuestion.setOption3(updatedQuestion.getOption3());
             existQuestion.setOption4(updatedQuestion.getOption4());
             existQuestion.setRightanswer(updatedQuestion.getRightanswer());
             questionDao.save(existQuestion);
             return new ResponseEntity<>("success",HttpStatus.CREATED);
         }catch (Exception e) {
			// TODO: handle exception
        	 e.printStackTrace();
		}
         return new ResponseEntity<>("failed",HttpStatus.BAD_REQUEST);
         
	}

	public ResponseEntity<String> deleteQuestion(int id) {
		// TODO Auto-generated method stub
		try {
			questionDao.deleteById(id);
			return new ResponseEntity<>("success",HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		   return new ResponseEntity<>("failed",HttpStatus.BAD_REQUEST);
	}
	

}

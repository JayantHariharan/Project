package com.lucifer.rankTest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lucifer.rankTest.model.Questions;

@Repository
public interface QuestionDao extends JpaRepository<Questions, Integer>{

	List<Questions> findByCategory(String category);

	@Query(value = "SELECT * FROM questions q WHERE q.category=:category ORDER BY RAND() LIMIT 5",nativeQuery = true)
	List<Questions> findRandomQuestionByCategory(String category);



}

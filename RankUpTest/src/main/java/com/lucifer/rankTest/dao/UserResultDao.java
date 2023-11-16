package com.lucifer.rankTest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucifer.rankTest.model.UserResult;

@Repository
public interface UserResultDao extends JpaRepository<UserResult, Integer>{

	
}

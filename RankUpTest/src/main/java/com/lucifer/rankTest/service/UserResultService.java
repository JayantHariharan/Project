package com.lucifer.rankTest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucifer.rankTest.dao.UserResultDao;
import com.lucifer.rankTest.model.Quiz;
import com.lucifer.rankTest.model.UserResult;

@Service
public class UserResultService {

    @Autowired
    UserResultDao userResultRepository;

    public int saveUserResult(String username, Quiz quiz, int result) {
        // Corrected the order of parameters in the UserResult constructor
        UserResult userResult = new UserResult(username, quiz, result);
        userResult.setUsername(username);
        userResult.setQuiz(quiz);
        userResult.setResult(result);
        UserResult savedUser =userResultRepository.save(userResult);
        return savedUser.getId();
    }
    public UserResult getUserResultById(Integer userId) {
        return userResultRepository.findById(userId).orElse(null);
    }

    public List<UserResult> getAllUserResults() {
        return userResultRepository.findAll();
    }
    // Other methods as needed...
}

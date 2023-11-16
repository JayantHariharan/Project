package com.lucifer.rankTest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.lucifer.rankTest.dao.QuestionDao;
import com.lucifer.rankTest.dao.QuizDao;
import com.lucifer.rankTest.dao.UserResultDao;
import com.lucifer.rankTest.model.Category;
import com.lucifer.rankTest.model.Questions;
import com.lucifer.rankTest.model.QuestionsWrapper;
import com.lucifer.rankTest.model.Quiz;
import com.lucifer.rankTest.model.UserResult;
import com.lucifer.rankTest.model.ValidateAnswer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class QuizService {

	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuestionDao questionDao;
	
	 @Autowired
	 UserResultService userResultService;
	
	 public ResponseEntity<String> createQuiz() {
	        try {
	            List<Questions> javaQuestions = questionDao.findRandomQuestionByCategory("Java");
	            List<Questions> cQuestions = questionDao.findRandomQuestionByCategory("C Programming");
	            List<Questions> pythonQuestions = questionDao.findRandomQuestionByCategory("Python");

	            Quiz quiz = new Quiz();
	            quiz.setJavaQuestions(javaQuestions);
	            quiz.setcQuestions(cQuestions);
	            quiz.setPythonQuestions(pythonQuestions);
	            quizDao.save(quiz);

	            // Return the newly created quiz's ID
	            return new ResponseEntity<>("Quiz Created " + quiz.getId(), HttpStatus.CREATED);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>("Quiz not Created", HttpStatus.BAD_REQUEST);
	        }
	    }
	 
	public int calculateResult(List<ValidateAnswer> responses, HttpServletRequest request) {
	    try {
	        HttpSession session = request.getSession();
	        Integer id = (Integer) session.getAttribute("id");
	        session.removeAttribute("id");

	        if (id == null) {
	            // Handle the case where quiz ID is not found in the session
	            return 0;
	        }

	        Quiz quiz = quizDao.findById(id).orElse(null);

	        if (quiz == null) {
	            // Handle the case where quiz is not found
	            return 0;
	        }

	        List<Questions> javaQuestions = quiz.getJavaQuestions();
	        List<Questions> cQuestions = quiz.getcQuestions();
	        List<Questions> pythonQuestions = quiz.getPythonQuestions();

	        if (javaQuestions == null || cQuestions == null || pythonQuestions == null ||
	            javaQuestions.size() != 5 || cQuestions.size() != 5 || pythonQuestions.size() != 5) {
	            // Handle the case where questions are not properly initialized or have unexpected sizes
	            return 0;
	        }

	        int rightAnswer = 0;

	        for (int i = 0; i < 5; i++) {
	            if (responses.get(i).getUserAnswer().equals(javaQuestions.get(i).getRightanswer())) {
	                rightAnswer++;
	            }
	        }

	        for (int i = 5; i < 10; i++) {
	            if (responses.get(i).getUserAnswer().equals(cQuestions.get(i - 5).getRightanswer())) {
	                rightAnswer++;
	            }
	        }

	        for (int i = 10; i < 15; i++) {
	            if (responses.get(i).getUserAnswer().equals(pythonQuestions.get(i - 10).getRightanswer())) {
	                rightAnswer++;
	            }
	        }

	        String userName = (String) session.getAttribute("userName");
	        session.removeAttribute("userName");
	        if (userName == null) {
	            // Handle the case where the username is not found in the session
	            return 0;
	        }

	        Integer userId =userResultService.saveUserResult(userName, quiz, rightAnswer);
	        System.out.println(userId);
	        return userId;
	    } catch (Exception e) {
	        // Handle exceptions more gracefully (log or provide meaningful response)
	        e.printStackTrace();
	        return 0;
	    }
	}


	
	

	public ResponseEntity<List<QuestionsWrapper>> getQuiz(int id, String userName, HttpServletRequest request) {
	    try {
	        HttpSession session = request.getSession();
	        session.setAttribute("userName", userName);
	        session.setAttribute("id", id);
	        
	        // Assuming you have a method to retrieve user-specific quiz based on userName
	        Optional<Quiz> quizOptional = quizDao.findById(id);

	        if (quizOptional.isPresent()) {
	            Quiz quiz = quizOptional.get();

	            List<Questions> javaQuestions = quiz.getJavaQuestions();
	            List<Questions> cQuestions = quiz.getcQuestions();
	            List<Questions> pythonQuestions = quiz.getPythonQuestions();

	            List<QuestionsWrapper> userQuestion = new ArrayList<>();

	            userQuestion.addAll(javaQuestions.stream().map(QuestionsWrapper::new).collect(Collectors.toList()));
	            userQuestion.addAll(cQuestions.stream().map(QuestionsWrapper::new).collect(Collectors.toList()));
	            userQuestion.addAll(pythonQuestions.stream().map(QuestionsWrapper::new).collect(Collectors.toList()));

	            return new ResponseEntity<>(userQuestion, HttpStatus.CREATED);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	
	public List<Category> categorizeQuestions(int id, List<QuestionsWrapper> allQuestions) {
	    List<Category> categories = new ArrayList<>();
	    List<QuestionsWrapper> javaQuestions = new ArrayList<>();
	    List<QuestionsWrapper> cQuestions = new ArrayList<>();
	    List<QuestionsWrapper> pythonQuestions = new ArrayList<>();

	    for (QuestionsWrapper question : allQuestions) {
	        String category = question.getCategory();
	        switch (category) {
	            case "Java":
	                javaQuestions.add(question);
	                break;
	            case "C Programming":
	                cQuestions.add(question);
	                break;
	            case "Python":
	                pythonQuestions.add(question);
	                break;
	            // Add more cases for other categories if needed
	        }
	    }

	    categories.add(new Category("Java", javaQuestions));
	    categories.add(new Category("C", cQuestions));
	    categories.add(new Category("Python", pythonQuestions));

	    return categories;
	}


	private String determineCategory(QuestionsWrapper question) {
	    String category = question.getCategory();
	    if (category != null) {
	        if (category.toLowerCase().contains("java")) {
	            return "Java";
	        } else if (category.toLowerCase().contains("c")) {
	            return "C";
	        } else if (category.toLowerCase().contains("python")) {
	            return "Python";
	        }
	    }
	    return "Unknown"; // or handle it according to your requirements
	}



	
	

	
}

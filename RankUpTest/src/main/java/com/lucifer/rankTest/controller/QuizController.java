package com.lucifer.rankTest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.lucifer.rankTest.model.Category;
import com.lucifer.rankTest.model.Questions;
import com.lucifer.rankTest.model.QuestionsWrapper;
import com.lucifer.rankTest.model.UserResult;
import com.lucifer.rankTest.model.ValidateAnswer;
import com.lucifer.rankTest.service.QuizService;
import com.lucifer.rankTest.service.UserResultService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class QuizController {

	@Autowired
	QuizService quizService;
	
	@Autowired
	UserResultService userResultService;
	
	private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    
    
    @GetMapping("/admin/alluserrecords")
    public String showAllUserRecords(Model model) {
        List<UserResult> userResults = userResultService.getAllUserResults();
        model.addAttribute("userResults", userResults);
        return "studentRecords";
    }
    
    @GetMapping("/login/admin-login")
    public String adminPage() {
    	return "adminLoginPage";
    }
	
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
    	System.out.println("admin Dashboard");
    	return "adminDashboard";
    }

    @PostMapping("/login/admin-login")
    public String adminLogin(@RequestParam String username, @RequestParam String password, Model model) {
        System.out.println("Post Login");
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            // If username and password are correct, redirect to the admin dashboard or another page
            return "redirect:/admin/dashboard";
        } else {
            // If username and password are incorrect, display an error message
            model.addAttribute("error", "Invalid username or password");
            return "redirect:/login/admin-login";
        }
    }
    
	@GetMapping("login")
	public String showLoginPage() {
		return "login";
	}
	
	@GetMapping("/login/student-login")
    public String showStudentLoginPage() {
        return "studentLoginPage"; 
    }
	
	
	 @GetMapping("/admin/createquiz")
	    public String showQuizId(Model model) {
	        // Display the adminDashboard without creating a quiz
	        return "adminDashboard";
	    }

	    @PostMapping("/admin/createquiz")
	    public String createQuiz(Model model) {
	        ResponseEntity<String> responseEntity = quizService.createQuiz();
	        String response = responseEntity.getBody();

	        model.addAttribute("response", response);  // Add the entire response
	        if (response.contains("Quiz Created")) {
	            String[] responseArray = response.split(" ");
	            String quizId = responseArray.length > 0 ? responseArray[responseArray.length - 1] : "";

	            model.addAttribute("quizId", quizId);  // Add only the Quiz ID to the model
	        }

	        return "adminDashboard";
	    }
	
	 @GetMapping("/student/getquiz") 
	 public String getQuiz(@RequestParam int id,@RequestParam String userName,HttpServletRequest request,Model model) {
	 List<QuestionsWrapper> questions = quizService.getQuiz(id, userName,request).getBody(); 
	 if (questions != null && !questions.isEmpty()) {
	 List<Category> categorizedQuestions = quizService.categorizeQuestions(id,questions); 
	 model.addAttribute("categories", categorizedQuestions);
	 System.out.println("getquiz "+categorizedQuestions); 
	 return "quiz"; 
	 	} 
	 return "error"; }
	 
	
	 @PostMapping("/student/getquiz")
	 public RedirectView submitQuiz(@RequestBody List<ValidateAnswer> responses, HttpServletRequest request, RedirectAttributes attributes) {
	     int userId = quizService.calculateResult(responses, request);
	     System.out.println("submit");
	     UserResult userResult = userResultService.getUserResultById(userId);

	     // Set flash attributes
	     attributes.addFlashAttribute("userId", userId);

	     // Redirect to the certificate page
	     HttpSession session=request.getSession();
	     session.setAttribute("id",userId);
	     return new RedirectView("/certificate", true);
	 }

	 
	 @GetMapping("/usercertificate")
	 public String userCertificate(HttpServletRequest request, Model model) {
	     HttpSession session = request.getSession();
	     Integer userId = (Integer) session.getAttribute("id");

	     if (userId != null) {
	         session.removeAttribute("id");
	         UserResult userResult = userResultService.getUserResultById(userId);
	         model.addAttribute("userName", userResult.getUsername());
	         model.addAttribute("userScore", userResult.getResult()+" Out of 15");
	         return "certificate";
	     } else {
	         // Handle the case where the userId attribute is null
	         return "error";
	     }
	 }


}

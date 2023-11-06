package com.lucifer.employee.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lucifer.employee.Model.Employee;
import com.lucifer.employee.dao.EmployeeRepo;
import com.lucifer.employee.service.EmployeeService;



@Controller
public class EmployeeController {
	@Autowired
	EmployeeRepo repo;
	@Autowired
	EmployeeService service;
	
	@RequestMapping("/newUser")
	public String newUser() {
		return "newUser";
	}
	
	@RequestMapping("/addUser")
	public ModelAndView addUser(Employee employee) {
		repo.save(employee);
		ModelAndView mv=new ModelAndView("newUser");
		String empid="Employee ID: "+employee.getId();
		mv.addObject("employee",empid);
		return mv;
	}

	@RequestMapping({"/","/searchUser"})
	public String searchUser() {
		return "searchUser";
	}
	@RequestMapping("/getEmployee")
	public ModelAndView getEmployee(@RequestParam int id) {
		ModelAndView mv=new ModelAndView("searchUser");
		Employee employee=repo.findById(id).orElse(new Employee());
		mv.addObject("fname","FirstName: "+employee.getFname());
		mv.addObject("lname","LastName: "+employee.getLname());
		mv.addObject("gender","Gender: "+employee.getGender());
		mv.addObject("phone","Phone: "+employee.getPhone());
		return mv;
	}
	
	@RequestMapping("/deleteUser")
	public String deleteUser() {
		return "deleteUser";
	}
	
	@RequestMapping("/deleteEmployee")
	public ModelAndView deleteEmployee(@RequestParam int id) {
		ModelAndView mv=new ModelAndView("deleteUser");
		Optional<Employee> employee=repo.findById(id);
		if(employee.isPresent()) {
			repo.delete(employee.get());
			mv.addObject("employee","record deleted");
		}else {
			mv.addObject("employee","no record exist");
		}
		return mv;
	}
	
	@RequestMapping("/updateUser")
	public String updateUser() {
		return "modifyUser";
	}
	
	@RequestMapping("/updateEmployee")
	public ModelAndView updateEmployee(@RequestParam int id,@RequestParam String fname,@RequestParam String lname,@RequestParam String gender,@RequestParam String phone) {
		
		ModelAndView mv=new ModelAndView("modifyUser");
		Optional<Employee> employeeOptional=repo.findById(id);
		if(employeeOptional.isPresent()) {
			Employee employee=employeeOptional.get();
			employee.setFname(fname);
			employee.setLname(lname);
			employee.setGender(gender);
			employee.setPhone(phone);
			repo.save(employee);
			mv.addObject("employee","employee updated");
		}else {
			mv.addObject("employee","no record exist");
		}
		return mv;
	}
	@RequestMapping("/employees")
	public String getAllEmployees(Model model) {
	    List<Employee> employees = service.allEmployee();
	    model.addAttribute("employees",employees);
	    return "allUser.html";
	}

	
	

	
	
}

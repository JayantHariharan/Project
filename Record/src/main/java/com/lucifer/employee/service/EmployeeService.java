package com.lucifer.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucifer.employee.Model.Employee;
import com.lucifer.employee.dao.EmployeeRepo;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo repo;
	
	public List<Employee>allEmployee(){
		return (List<Employee>) repo.findAll();
	}
}

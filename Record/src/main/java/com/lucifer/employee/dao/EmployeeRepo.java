package com.lucifer.employee.dao;

import org.springframework.data.repository.CrudRepository;

import com.lucifer.employee.Model.Employee;

public interface EmployeeRepo extends CrudRepository<Employee, Integer> {


	
}

package com.employeespringbootglobalexceptionhandling.demo.service;

import java.util.List;

import com.employeespringbootglobalexceptionhandling.demo.entity.Employee;

public interface EmployeeServiceInterface {

	Employee addEmployee(Employee employee);

	List<Employee> getAllEmployee();

	List<Employee> getEmployeeByList(List<Long> empIdsList);
	
	Employee getEmployeeById(Long empId);

	void deleteEmployeeById(Long empId);
	
}

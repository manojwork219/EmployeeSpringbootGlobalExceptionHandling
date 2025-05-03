package com.employeespringbootglobalexceptionhandling.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeespringbootglobalexceptionhandling.demo.entity.Employee;
import com.employeespringbootglobalexceptionhandling.demo.service.EmployeeServiceInterface;

@RestController
@RequestMapping("/code")
public class EmployeeController {

	@Autowired
	EmployeeServiceInterface employeeServiceInterface;

	@PostMapping("/save")
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {

		Employee employeeSaved = employeeServiceInterface.addEmployee(employee);
		return new ResponseEntity<>(employeeSaved, HttpStatus.CREATED);

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllEmployee() {
		List<Employee> listOfEmployee = employeeServiceInterface.getAllEmployee();
		return new ResponseEntity<>(listOfEmployee, HttpStatus.OK);
	}

	@GetMapping("/emp/{empId}")
	public ResponseEntity<?> getAllEmployee(@PathVariable("empId") Long empId) {

		Employee employee = employeeServiceInterface.getEmployeeById(empId);
		return new ResponseEntity<>(employee, HttpStatus.OK);

	}

	@DeleteMapping("/delete/emp/{empId}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("empId") Long empId) {

		employeeServiceInterface.deleteEmployeeById(empId);
		return new ResponseEntity<String>("Employee Deleted with Id: "+empId,HttpStatus.ACCEPTED);

	}

	@PutMapping("/update")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {

		Employee employeeSaved = employeeServiceInterface.addEmployee(employee);
		return new ResponseEntity<>(employeeSaved, HttpStatus.CREATED);

	}

}

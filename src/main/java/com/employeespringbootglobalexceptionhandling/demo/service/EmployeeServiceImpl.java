package com.employeespringbootglobalexceptionhandling.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeespringbootglobalexceptionhandling.demo.custom.exception.BusinessException;
import com.employeespringbootglobalexceptionhandling.demo.custom.exception.EmptyInputException;
import com.employeespringbootglobalexceptionhandling.demo.entity.Employee;
import com.employeespringbootglobalexceptionhandling.demo.repository.EmployeeCRUDRepository;

@Service
public class EmployeeServiceImpl implements EmployeeServiceInterface {

	@Autowired
	EmployeeCRUDRepository employeeCRUDRepository;

	@Override
	public Employee addEmployee(Employee employee) {

		if (employee.getName() == null || employee.getName().trim().isEmpty()) {
			throw new EmptyInputException("601", "Please send proper Name, Its blank or null");
		}

		Employee savedEmployee = employeeCRUDRepository.save(employee);
		return savedEmployee;
	}

	@Override
	public List<Employee> getAllEmployee() {
		List<Employee> employeeList = null;
		employeeList = employeeCRUDRepository.findAll();

		if (employeeList.isEmpty())
			throw new BusinessException("604", "No Employee Exists, List is empty...");

		return employeeList;
	}

	@Override
	public Employee getEmployeeById(Long empId) {
		return employeeCRUDRepository.findById(empId).get();
	}

	@Override
	public void deleteEmployeeById(Long empId) {

		try {
			employeeCRUDRepository.deleteById(empId);
		} catch (IllegalArgumentException e) {
			throw new BusinessException("608",
					"Employee is Null, please send an Employee id to be deleted " + e.getMessage());
		}
	}
	
	

}

package com.employeespringbootglobalexceptionhandling.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employeespringbootglobalexceptionhandling.demo.entity.Employee;

@Repository
public interface EmployeeCRUDRepository extends JpaRepository<Employee, Long>{

	List<Employee> findEmployeeByIdIn(List<Long> empIdsList);
}

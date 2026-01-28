
package com.employeespringbootglobalexceptionhandling.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.employeespringbootglobalexceptionhandling.demo.entity.Employee;
import com.employeespringbootglobalexceptionhandling.demo.service.EmployeeServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeServiceInterface employeeServiceInterface;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void testAddEmployee() throws Exception {
		Employee mockEmployee = new Employee();
		mockEmployee.setId(1L);
		mockEmployee.setName("John");

		when(employeeServiceInterface.addEmployee(any(Employee.class))).thenReturn(mockEmployee);

		mockMvc.perform(post("/Employee/save").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockEmployee))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.name").value("John"));
	}

	@Test
	void testGetAllEmployees() throws Exception {
		Employee e1 = new Employee();
		e1.setId(1L);
		e1.setName("John");

		Employee e2 = new Employee();
		e2.setId(2L);
		e2.setName("Jane");

		List<Employee> employeeList = Arrays.asList(e1, e2);

		when(employeeServiceInterface.getAllEmployee()).thenReturn(employeeList);

		mockMvc.perform(get("/Employee/all")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	void testGetEmployeeById() throws Exception {
		Employee mockEmployee = new Employee();
		mockEmployee.setId(1L);
		mockEmployee.setName("John");

		when(employeeServiceInterface.getEmployeeById(1L)).thenReturn(mockEmployee);

		mockMvc.perform(get("/Employee/emp/1")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("John"));
	}

	@Test
	void testDeleteEmployeeById() throws Exception {
		doNothing().when(employeeServiceInterface).deleteEmployeeById(1L);

		mockMvc.perform(delete("/Employee/delete/emp/1")).andExpect(status().isAccepted())
				.andExpect(content().string("Employee Deleted with Id: 1"));
	}

	@Test
	void testUpdateEmployee() throws Exception {
		Employee mockEmployee = new Employee();
		mockEmployee.setId(1L);
		mockEmployee.setName("John Updated");

		when(employeeServiceInterface.addEmployee(any(Employee.class))).thenReturn(mockEmployee);

		mockMvc.perform(put("/Employee/update").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockEmployee))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("John Updated"));
	}
}

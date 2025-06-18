package com.employeespringbootglobalexceptionhandling.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.employeespringbootglobalexceptionhandling.demo.custom.exception.BusinessException;
import com.employeespringbootglobalexceptionhandling.demo.custom.exception.EmptyInputException;
import com.employeespringbootglobalexceptionhandling.demo.entity.Employee;
import com.employeespringbootglobalexceptionhandling.demo.repository.EmployeeCRUDRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeCRUDRepository employeeCRUDRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testAddEmployee_ValidInput_ReturnsSavedEmployee() {
        Employee employee = new Employee();
        employee.setName("John");

        when(employeeCRUDRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.addEmployee(employee);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(employeeCRUDRepository, times(1)).save(employee);
    }

    @Test
    void testAddEmployee_EmptyName_ThrowsEmptyInputException() {
        Employee employee = new Employee(); // name is null

        EmptyInputException exception = assertThrows(EmptyInputException.class, () -> {
            employeeService.addEmployee(employee);
        });

        assertEquals("601", exception.getErrorCode());
        verify(employeeCRUDRepository, never()).save(any());
    }

    @Test
    void testGetAllEmployee_WhenDataExists_ReturnsList() {
        List<Employee> mockList = Arrays.asList(new Employee(), new Employee());

        when(employeeCRUDRepository.findAll()).thenReturn(mockList);

        List<Employee> result = employeeService.getAllEmployee();

        assertEquals(2, result.size());
        verify(employeeCRUDRepository, times(1)).findAll();
    }

    @Test
    void testGetAllEmployee_WhenEmpty_ThrowsBusinessException() {
        when(employeeCRUDRepository.findAll()).thenReturn(Arrays.asList());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            employeeService.getAllEmployee();
        });

        assertEquals("604", exception.getErrorCode());
    }

    @Test
    void testGetEmployeeById_ReturnsEmployee() {
        Employee mockEmp = new Employee();
        mockEmp.setId(1L);
        mockEmp.setName("Alice");

        when(employeeCRUDRepository.findById(1L)).thenReturn(Optional.of(mockEmp));

        Employee result = employeeService.getEmployeeById(1L);

        assertEquals("Alice", result.getName());
        verify(employeeCRUDRepository).findById(1L);
    }

    @Test
    void testDeleteEmployeeById_Success() {
        // No exception expected
        doNothing().when(employeeCRUDRepository).deleteById(1L);

        employeeService.deleteEmployeeById(1L);

        verify(employeeCRUDRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEmployeeById_ThrowsBusinessException() {
        doThrow(new IllegalArgumentException("id is null")).when(employeeCRUDRepository).deleteById(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            employeeService.deleteEmployeeById(null);
        });

        assertEquals("608", exception.getErrorCode());
    }
}

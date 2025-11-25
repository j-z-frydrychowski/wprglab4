package com.test;

import com.test.exception.ApiException;
import com.test.model.Employee;
import com.test.service.ApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApiServiceTest {

    private ApiService apiService;

    @BeforeEach
    public void setUp() {
        apiService = new ApiService();
    }

    @Test
    public void testFetchEmployeesApiNotNull() throws ApiException {
        List<Employee> employees = apiService.fetchEmployeesFromApi();
        assertNotNull(employees);
    }

    @Test
    public void testFetchEmployeesApiNonEmptyList() throws ApiException {
        List<Employee> employees = apiService.fetchEmployeesFromApi();
        assertTrue(employees.size() > 0);
    }

    @Test
    public void testFetchEmployeesApiEmployeeWithName() throws ApiException {
        List<Employee> employees = apiService.fetchEmployeesFromApi();
        Employee firstEmployee = employees.get(0);
        assertNotNull(firstEmployee.getName());
    }

    @Test
    public void testFetchEmployeesApiEmployeeWithEmail() throws ApiException {
        List<Employee> employees = apiService.fetchEmployeesFromApi();
        Employee firstEmployee = employees.get(0);
        assertNotNull(firstEmployee.getEmail());
    }

    @Test
    public void testFetchEmployeesApiEmployeeWithCompany() throws ApiException {
        List<Employee> employees = apiService.fetchEmployeesFromApi();
        Employee firstEmployee = employees.get(0);
        assertNotNull(firstEmployee.getCompanyName());
    }

    @Test
    public void testFetchEmployeesApiEmployeeWithPosition() throws ApiException {
        List<Employee> employees = apiService.fetchEmployeesFromApi();
        Employee firstEmployee = employees.get(0);
        assertNotNull(firstEmployee.getJobTitle());
    }

    @Test
    public void testMapApiUserEmployeeHasFirstName() throws ApiException {
        List<Employee> employees = apiService.fetchEmployeesFromApi();
        Employee firstEmployee = employees.get(0);
        assertNotNull(firstEmployee.getName());
    }

    @Test
    public void testMapApiUserEmployeeHasLastName() throws ApiException {
        List<Employee> employees = apiService.fetchEmployeesFromApi();
        Employee firstEmployee = employees.get(0);
        assertNotNull(firstEmployee.getSurname());
    }

    @Test
    public void testDefaultPositionProgramista() throws ApiException {
        List<Employee> employees = apiService.fetchEmployeesFromApi();
        Employee firstEmployee = employees.get(0);
        assertEquals("Programista", firstEmployee.getJobTitle().getName());
    }

    @Test
    public void testDefaultPositionSalary() throws ApiException {
        List<Employee> employees = apiService.fetchEmployeesFromApi();
        Employee firstEmployee = employees.get(0);
        assertEquals(8000, firstEmployee.getJobTitle().getSalary());
    }
}
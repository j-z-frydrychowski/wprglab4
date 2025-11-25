package com.test;

import com.test.model.CompanyStatistics;
import com.test.model.Employee;
import com.test.model.Position;

import com.test.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {
    private EmployeeService employeeService;
    private Employee jan, anna, piotr, kris;

    @BeforeEach
    public void setUp(){
        employeeService = new EmployeeService();
        jan = new Employee("Jan", "Kowalski", "jank@techcorp.pl", "TechCorp", Position.PROGRAMISTA);
        anna = new Employee("Anna", "Nowak", "annan@techcorp.pl", "TechCorp", Position.MANAGER);
        piotr = new Employee("Piotr", "Adamski", "piotra@otherco.com", "OtherCo", Position.PREZES);

        kris = new Employee("Kris", "Niski", "kris.n@tech.com", "TechCorp", Position.PROGRAMISTA);
        kris.setSalary(7000);

        employeeService.addEmployee(jan);
        employeeService.addEmployee(anna);
        employeeService.addEmployee(piotr);
    }

    @Test
    public void testAddEmployeeIncreseSizeByOne(){
        Employee newEmployee = new Employee("Adam", "Nowy", "adam.n@new.com", "NewCo", Position.STAZYSTA);

        employeeService.addEmployee(newEmployee);

        assertEquals(4, employeeService.getEmployees().size());
    }

    @Test
    public void testSearchEmployeeByEmail_returnsEmployee(){
        assertNotNull(employeeService.SearchEmployee(jan.getEmail()));
    }

    @Test
    public void testSearchEmployeeByPhone_returnsNull(){
        assertNull(employeeService.SearchEmployee("nieistnieje@mail.com"));
    }

    @Test
    public void testSearchEmployeeByCompany_correctSize() {
        List<Employee> techCorpEmployees = employeeService.SearchEmployeeByCompany("TechCorp");

        assertEquals(2, techCorpEmployees.size());
    }
    @Test
    public void testSearchEmployeeByCompany_returnsEmptyList() {
        List<Employee> noExistingCompanyEmployees = employeeService.SearchEmployeeByCompany("nieIstnieje");

        assertTrue(noExistingCompanyEmployees.isEmpty());
    }

    @Test
    public void testValidateSalaryConsistency_findsUnderpaidEmployee() {
        employeeService.addEmployee(kris);

        List<Employee> inconsistent = employeeService.validateSalaryConsistency();

        assertEquals(1, inconsistent.size());
    }

    @Test
    void testValidateSalaryConsistency_zeroUnderpaid() {
        List<Employee> inconsistent = employeeService.validateSalaryConsistency();

        assertTrue(inconsistent.isEmpty());
    }

    @Test
    void testCompanyStatistics_correctEmployeeCount() {
        Map<String, CompanyStatistics> stats = employeeService.getCompanyStatistics();

        assertEquals(2, stats.get("TechCorp").getEmployeeCount());
    }

    @Test
    void testCompanyStatistics_correctAverageSalary() {
        Map<String, CompanyStatistics> stats = employeeService.getCompanyStatistics();

        assertEquals(10000.00, stats.get("TechCorp").getAverageSalary());
    }

    @Test
    void testCompanyStatistics_correctHighestPaidEmployee() {
        Map<String, CompanyStatistics> stats = employeeService.getCompanyStatistics();

        assertEquals("Anna Nowak", stats.get("TechCorp").getHighestPaidEmployeeName());
    }

    @Test
    void testCompanyStatistics_singleEmployeeCompany_correctHighestPaid() {
        Map<String, CompanyStatistics> stats = employeeService.getCompanyStatistics();

        assertEquals("Piotr Adamski", stats.get("OtherCo").getHighestPaidEmployeeName());
    }
}
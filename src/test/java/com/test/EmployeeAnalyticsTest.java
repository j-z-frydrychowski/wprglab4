package com.test;

import com.test.model.Employee;
import com.test.model.Position;
import com.test.service.EmployeeAnalytics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeAnalyticsTest {

    private EmployeeAnalytics employeeAnalytics;
    private Employee e1, e2, e3, e4, e5;
    private ArrayList<Employee> employees = new ArrayList<>();

    @BeforeEach
    public void setup() {
        e1 = new Employee("Jan", "Kowalski", "jan@tech.com", "TechCorp", Position.PROGRAMISTA);
        e2 = new Employee("Anna", "Nowak", "anna@tech.com", "TechCorp", Position.MANAGER);
        e3 = new Employee("Piotr", "Adamski", "piotr@other.com", "OtherCo", Position.PREZES);
        e4 = new Employee("Zofia", "Zacna", "zofia@small.com", "SmallBiz", Position.STAZYSTA);
        e5 = new Employee("Marek", "Zygmunt", "marek@tech.com", "TechCorp", Position.PROGRAMISTA);

        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        employees.add(e4);
        employees.add(e5);

        employeeAnalytics = new EmployeeAnalytics(employees);
    }

    @Test
    public void testSortEmployeesBySurname_firstElementCorrect() {
        List<Employee> sorted = employeeAnalytics.SortEmployeesBySurname();

        assertEquals("Adamski", sorted.get(0).getSurname());
    }

    @Test
    void testSortEmployeesBySurname_lastElementIsCorrect() {
        List<Employee> sorted = employeeAnalytics.SortEmployeesBySurname();

        assertEquals("Zygmunt", sorted.get(4).getSurname());
    }

    @Test
    void testGroupEmployeesByPosition_oneManager() {
        Map<Position, List<Employee>> groups = employeeAnalytics.GroupEmployeesByPosition();

        assertEquals(1, groups.get(Position.MANAGER).size());
    }

    @Test
    void testGroupEmployeesByPosition_twpProgramista() {
        Map<Position, List<Employee>> groups = employeeAnalytics.GroupEmployeesByPosition();

        assertEquals(2, groups.get(Position.PROGRAMISTA).size());
    }

    @Test
    void testCountEmployeesByPosition_twoProgramista() {
        Map<Position, Long> counts = employeeAnalytics.CountEmployeesByPosition();

        assertEquals(2L, counts.get(Position.PROGRAMISTA));
    }

    @Test
    void testCountEmployeesByPosition_onePrezes() {
        Map<Position, Long> counts = employeeAnalytics.CountEmployeesByPosition();

        assertEquals(1L, counts.get(Position.PREZES));
    }

    @Test
    void testCountEmployeesByPosition_empty() {
        EmployeeAnalytics emptyAnalytics = new EmployeeAnalytics(new ArrayList<>());

        Map<Position, Long> counts = emptyAnalytics.CountEmployeesByPosition();

        assertTrue(counts.isEmpty());
    }

    @Test
    void testCalculateAverageSalary_correctValue() {
        double expectedAverage = 11200.00;

        double actualAverage = employeeAnalytics.CalculateAverageSalary();

        assertEquals(expectedAverage, actualAverage);
    }

    @Test
    void testCalculateAverageSalary_returnsZero() {
        EmployeeAnalytics emptyAnalytics = new EmployeeAnalytics(new  ArrayList<>());

        double actualAverage = emptyAnalytics.CalculateAverageSalary();

        assertEquals(0.0, actualAverage);
    }

    @Test
    void testGetHighestPaidEmployee_returnsTrue() {
        Optional<Employee> highest = employeeAnalytics.GetHighestPaidEmployee();

        assertEquals(e3.getEmail(), highest.get().getEmail());
    }

    @Test
    void testGetHighestPaidEmployee_emptyList() {
        EmployeeAnalytics emptyAnalytics = new EmployeeAnalytics(new  ArrayList<>());

        Optional<Employee> highest = emptyAnalytics.GetHighestPaidEmployee();

        assertTrue(highest.isEmpty());
    }
}

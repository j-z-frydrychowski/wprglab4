package com.test;

import com.test.model.Employee;
import com.test.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Provider;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    private Position defPosition;
    private Employee employee;

    @BeforeEach
    void setUp() {
        defPosition = Position.getPosition("Programista");
        employee = new Employee("Jan", "Kowalski", "jankowalski@techcorp.pl", "TechCorp", defPosition);
    }

    @Test
    public void testConstructor() {
        Employee employee = new Employee();
        assertEquals("TechCorp", employee.getCompanyName());
    }

    @Test
    public void testConstructorSetBaseSalary() {
        assertEquals(defPosition.getSalary(), employee.getSalary());
    }

    @Test
    public void testGetName(){
        assertEquals("Jan", employee.getName());
    }

    @Test
    public void testGetSurname(){
        assertEquals("Kowalski", employee.getSurname());
    }

    @Test
    public void testGetEmail(){
        assertEquals("jankowalski@techcorp.pl", employee.getEmail());
    }

    @Test
    public void testGetCompanyName(){
        assertEquals("TechCorp", employee.getCompanyName());
    }

    @Test
    public void testGetJobTitle(){
        assertEquals(defPosition, employee.getJobTitle());
    }

    @Test
    public void testGetSalary(){
        assertEquals(defPosition.getSalary(), employee.getSalary());
    }

    @Test
    public void testSetName(){
        employee.setName("John");

        assertEquals("John", employee.getName());
    }

    @Test
    public void testSetSurname(){
        employee.setSurname("Nowak");

        assertEquals("Nowak", employee.getSurname());
    }

    @Test
    public void testSetEmail(){
        employee.setEmail("johnnowak@techcorp.pl");

        assertEquals("johnnowak@techcorp.pl", employee.getEmail());
    }

    @Test
    public void testSetEmailIsNull(){
        assertThrows(IllegalArgumentException.class,() -> employee.setEmail(null));
    }

    @Test
    public void testSetCompanyName(){
        employee.setCompanyName("CorpTech");

        assertEquals("CorpTech", employee.getCompanyName());
    }

    @Test
    public void testSetJobTitle(){
        defPosition = Position.getPosition("Manager");
        employee.setJobTitle(defPosition);

        assertEquals(defPosition, employee.getJobTitle());
    }

    @Test
    public void testSetSalary(){
        employee.setSalary(1000);
        assertEquals(1000, employee.getSalary());
    }

    @Test
    public void testSetSalaryThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> employee.setSalary(-1));
    }

    @Test
    public void testEqualsSameObject(){
        assertEquals(employee, employee);
    }

    @Test
    public void testEqualsSameEmail(){
        Employee newEmployee = new Employee();
        newEmployee.setEmail("jankowalski@techcorp.pl");
        assertEquals(employee, newEmployee);
    }
    @Test
    public void testEqualsDifferentEmail(){
        Employee newEmployee = new Employee("Jan", "Kowalski", "jankowalski1@techcorp.pl", "TechCorp", defPosition);
        assertNotEquals(employee, newEmployee);
    }

    @Test
    public void testEqualsNull(){
        assertNotEquals(null, employee);
    }

    @Test
    public void testHashCodeSameEmail(){
        Employee newEmployee = new Employee();
        newEmployee.setEmail("jankowalski@techcorp.pl");
        assertEquals(employee.hashCode(), newEmployee.hashCode());
    }

    @Test
    public void testToString(){
        assertEquals("Imie: Jan\nSurname: Kowalski\nEmail: jankowalski@techcorp.pl\nCompany: TechCorp\nJob title: Programista\nSalary: 8000.0\n", employee.toString());
    }
}

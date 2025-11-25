package com.test;

import com.test.model.CompanyStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyStatisticsTest {
    private CompanyStatistics statistics;

    @BeforeEach
    public void setUp() {
        statistics = new CompanyStatistics(5, 12000.50, "John Doe");
    }

    @Test
    public void testConstructorNotNull() {
        assertNotNull(statistics);
    }

    @Test
    public void testGetEmployeeCount() {
        assertEquals(5, statistics.getEmployeeCount());
    }

    @Test
    public void testGetAverageSalary() {
        assertEquals(12000.50, statistics.getAverageSalary());
    }

    @Test
    public void testGetHighestPaidEmployee() {
        assertEquals("John Doe", statistics.getHighestPaidEmployeeName());
    }

    @Test
    public void testConstructorZeroEmployeeCount() {
        CompanyStatistics stats = new CompanyStatistics(0, 0, "N/A");
        assertEquals(0, stats.getEmployeeCount());
    }

    @Test
    public void testConstructorAvgSalaryZero() {
        CompanyStatistics stats = new CompanyStatistics(0, 0, "N/A");
        assertEquals(0, stats.getAverageSalary());
    }

    @Test
    public void testConstructorHighestPaidEmployeeZero() {
        CompanyStatistics stats = new CompanyStatistics(0, 0, "N/A");
        assertEquals("N/A", stats.getHighestPaidEmployeeName());
    }

    @Test
    public void testConstructorLargeEmployeeCount() {
        CompanyStatistics stats = new CompanyStatistics(1000, 50000.75, "Jane Smith");
        assertEquals(1000, stats.getEmployeeCount());
    }

    @Test
    public void testConstructorLargeAverageSalary() {
        CompanyStatistics stats = new CompanyStatistics(1000, 50000.75, "Jane Smith");
        assertEquals(50000.75, stats.getAverageSalary());
    }

    @Test
    public void testConstructorHighestPaidEmployeeLarge() {
        CompanyStatistics stats = new CompanyStatistics(1000, 50000.75, "Jane Smith");
        assertEquals("Jane Smith", stats.getHighestPaidEmployeeName());
    }

    @Test
    public void testToStringNotNull() {
        String result = statistics.toString();
        assertNotNull(result);
    }

    @Test
    public void testToStringContainsEmployeeCount() {
        String result = statistics.toString();
        assertTrue(result.contains("5"));
    }

    @Test
    public void testToStringContainsAverageSalary() {
        String result = statistics.toString();
        assertTrue(result.contains("12000,50"));
    }

    @Test
    public void testToStringContainsEmployeeName() {
        String result = statistics.toString();
        assertTrue(result.contains("John Doe"));
    }

    @Test
    public void testToStringFormatsAverageSalary2Decimals() {
        CompanyStatistics stats = new CompanyStatistics(3, 10000.333, "Alice");
        String result = stats.toString();
        System.out.println(result);
        assertTrue(result.contains("10000,33"));
    }
}
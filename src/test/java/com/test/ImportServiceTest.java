package com.test;

import com.opencsv.exceptions.CsvException;
import com.test.model.ImportSummary;
import com.test.service.EmployeeService;
import com.test.service.ImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ImportServiceTest {

    private ImportService importService;
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        employeeService = new EmployeeService();
        importService = new ImportService(employeeService);
    }

    @Test
    public void testImportCsvSummaryNotNull() throws IOException, CsvException {
        ImportSummary summary = importService.importFromCsv("employees.csv");
        assertNotNull(summary);
    }

    @Test
    public void testImportCsvReturnsNotNull() throws IOException, CsvException {
        ImportSummary summary = importService.importFromCsv("nonexistent_file.csv");
        assertNotNull(summary);
    }

    @Test
    public void testImportCsvNonExistentFileRecordsErrors() throws IOException, CsvException {
        ImportSummary summary = importService.importFromCsv("nonexistent_file.csv");
        assertTrue(summary.getErrors().size() > 0);
    }

    @Test
    public void testImportedCountNonNegative() throws IOException, CsvException {
        ImportSummary summary = importService.importFromCsv("employees.csv");
        assertTrue(summary.getImportedCount() >= 0);
    }

    @Test
    public void testImportedCountMatchesServiceCount() throws IOException, CsvException {
        ImportSummary summary = importService.importFromCsv("employees.csv");
        assertEquals(summary.getImportedCount(), employeeService.getEmployees().size());
    }

    @Test
    public void testErrorTrackingReturnsNotNull() throws IOException, CsvException {
        ImportSummary summary = importService.importFromCsv("employees.csv");
        assertNotNull(summary.getErrors());
    }

    @Test
    public void testEmployeesAddedToService() throws IOException, CsvException {
        int initialSize = employeeService.getEmployees().size();
        ImportSummary summary = importService.importFromCsv("employees.csv");
        int finalSize = employeeService.getEmployees().size();
        assertEquals(initialSize + summary.getImportedCount(), finalSize);
    }
}
package com.test;

import com.test.model.ImportSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImportSummaryTest {

    private ImportSummary importSummary;

    @BeforeEach
    public void setUp() {
        importSummary = new ImportSummary();
    }

    @Test
    public void testInitialCountIsZero() {
        assertEquals(0, importSummary.getImportedCount());
    }

    @Test
    public void testInitialErrorsListEmpty() {
        assertTrue(importSummary.getErrors().isEmpty());
    }

    @Test
    public void testIncrementImportedCountOne() {
        importSummary.incrementImportedCount();
        assertEquals(1, importSummary.getImportedCount());
    }

    @Test
    public void testAddErrorIncreasesErrorListSize() {
        importSummary.addError(1, "Invalid position");
        assertEquals(1, importSummary.getErrors().size());
    }

    @Test
    public void testAddMultipleErrorsIncreasesSizeCorrectly() {
        importSummary.addError(1, "Invalid position");
        importSummary.addError(2, "Duplicate email");
        importSummary.addError(3, "Invalid salary");
        assertEquals(3, importSummary.getErrors().size());
    }

    @Test
    public void testAddErrorContainsLineNumber() {
        importSummary.addError(5, "Test error");
        String error = importSummary.getErrors().get(0);
        assertTrue(error.contains("5"));
    }

    @Test
    public void testAddErrorContainsErrorDescription() {
        importSummary.addError(1, "Custom error message");
        String error = importSummary.getErrors().get(0);
        assertTrue(error.contains("Custom error message"));
    }

    @Test
    public void testAddErrorFormatsLineNumber() {
        importSummary.addError(10, "Error");
        String error = importSummary.getErrors().get(0);
        assertTrue(error.contains("Linia 10"));
    }

    @Test
    public void testToStringErrorReturnsNotNull() {
        importSummary.incrementImportedCount();
        String result = importSummary.toString();
        assertNotNull(result);
    }

    @Test
    public void testToStringContainsImportedCount() {
        importSummary.incrementImportedCount();
        String result = importSummary.toString();
        assertTrue(result.contains("1"));
    }

    @Test
    public void testToStringContainsZeroErrors() {
        importSummary.incrementImportedCount();
        String result = importSummary.toString();
        assertTrue(result.contains("0"));
    }

    @Test
    public void testToStringWithErrorsContainsImportedCount() {
        importSummary.incrementImportedCount();
        importSummary.incrementImportedCount();
        importSummary.addError(1, "Error message 1");
        String result = importSummary.toString();
        assertTrue(result.contains("2"));
    }

    @Test
    public void testToStringWithErrorsContainsFirstError() {
        importSummary.incrementImportedCount();
        importSummary.addError(1, "Error message 1");
        importSummary.addError(2, "Error message 2");
        String result = importSummary.toString();
        assertTrue(result.contains("Error message 1"));
    }

    @Test
    public void testToStringWithErrorsContainsSecondError() {
        importSummary.incrementImportedCount();
        importSummary.addError(1, "Error message 1");
        importSummary.addError(2, "Error message 2");
        String result = importSummary.toString();
        assertTrue(result.contains("Error message 2"));
    }
}
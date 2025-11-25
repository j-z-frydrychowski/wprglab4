package com.test;

import com.test.exception.ApiException;
import com.test.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionTest {

    @Test
    public void testApiExceptionContainsMessage() {
        String message = "API connection failed";
        ApiException exception = new ApiException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testApiExceptionContainsCause() {
        RuntimeException cause = new RuntimeException("Original error");
        ApiException exception = new ApiException("API error occurred", cause);
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testApiExceptionCauseContainsMessage() {
        ApiException exception = new ApiException("API error occurred", new RuntimeException("Original error"));
        assertEquals("API error occurred", exception.getMessage());
    }

    @Test
    public void testApiExceptionThrown() {
        assertThrows(ApiException.class, () -> {
            throw new ApiException("Test exception");
        });
    }

    @Test
    public void testInvalidDataExceptionContainsMessage() {
        String message = "Invalid data provided";
        InvalidDataException exception = new InvalidDataException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testInvalidDataExceptionContainsCause() {
        NumberFormatException cause = new NumberFormatException("Invalid number");
        InvalidDataException exception = new InvalidDataException("Data validation failed", cause);
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testInvalidDataExceptionCauseContainsMessage() {
        InvalidDataException exception = new InvalidDataException("Data validation failed", new NumberFormatException("Invalid number"));
        assertEquals("Data validation failed", exception.getMessage());
    }

    @Test
    public void testInvalidDataExceptionThrown() {
        assertThrows(InvalidDataException.class, () -> {
            throw new InvalidDataException("Test exception");
        });
    }

    @Test
    public void testApiExceptionExtendsRuntimeException() {
        assertTrue(RuntimeException.class.isAssignableFrom(ApiException.class));
    }

    @Test
    public void testInvalidDataExceptionExtendsRuntimeException() {
        assertTrue(RuntimeException.class.isAssignableFrom(InvalidDataException.class));
    }
}
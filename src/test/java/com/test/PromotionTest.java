package com.test;

import com.test.exception.InvalidDataException;
import com.test.model.Employee;
import com.test.model.Position;
import com.test.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PromotionTest {

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
    }

    static Stream<Arguments> validPromotionProvider() {
        return Stream.of(
                Arguments.of(Position.STAZYSTA, Position.PROGRAMISTA),
                Arguments.of(Position.PROGRAMISTA, Position.MANAGER),
                Arguments.of(Position.MANAGER, Position.WICEPREZES)
        );
    }

    @ParameterizedTest(name = "Awans z {0} na {1} powinien zaktualizować stanowisko i pensję")
    @MethodSource("validPromotionProvider")
    void shouldPromoteEmployeeValidPath(Position startPosition, Position targetPosition) {
        // GIVEN
        Employee employee = new Employee("Jan", "Test", "jan@test.pl", "Corp", startPosition);
        employeeService.addEmployee(employee);

        // WHEN
        employeeService.promoteEmployee(employee, targetPosition);

        // THEN
        // AssertJ - weryfikacja stanu obiektu
        assertThat(employee)
                .extracting(Employee::getJobTitle, Employee::getSalary)
                .containsExactly(targetPosition, targetPosition.getSalary());

        // Hamcrest - dodatkowe asercje czytelnościowe
        assertThat(employee.getJobTitle(), equalTo(targetPosition));
        assertThat(employee.getSalary(), greaterThan(startPosition.getSalary()));
        assertThat(employee.getSalary(), closeTo(targetPosition.getSalary(), 0.01));
    }

    @ParameterizedTest(name = "Próba awansu ze {0} na {1} powinna rzucić wyjątek (Błąd hierarchii)")
    @CsvSource({
            "MANAGER, PROGRAMISTA",
            "PREZES, STAZYSTA",
            "PROGRAMISTA, PROGRAMISTA"
    })
    void shouldThrowExceptionForInvalidHierarchy(Position startPosition, Position targetPosition) {
        // GIVEN
        Employee employee = new Employee("Jan", "Test", "jan@test.pl", "Corp", startPosition);

        // WHEN & THEN - AssertJ Fluent API
        assertThatThrownBy(() -> employeeService.promoteEmployee(employee, targetPosition))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Invalid promotion path");
    }
}
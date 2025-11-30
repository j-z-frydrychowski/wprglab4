package com.test;

import com.test.model.Employee;
import com.test.model.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SeniorityTest {

    // Źródło danych: data zatrudnienia, data odniesienia ("dziś"), oczekiwane lata
    static Stream<Arguments> seniorityProvider() {
        return Stream.of(
                // Proste przypadki
                Arguments.of(LocalDate.of(2020, 1, 1), LocalDate.of(2021, 1, 1), 1),
                Arguments.of(LocalDate.of(2010, 1, 1), LocalDate.of(2020, 6, 15), 10),

                // Niepełny rok
                Arguments.of(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 31), 0),

                // Rok przestępny (29 luty 2020 -> 28 luty 2021 to niecały rok)
                Arguments.of(LocalDate.of(2020, 2, 29), LocalDate.of(2021, 2, 28), 0),
                Arguments.of(LocalDate.of(2020, 2, 29), LocalDate.of(2021, 3, 1), 1)
        );
    }

    @ParameterizedTest(name = "Zatrudniony {0}, na dzień {1} powinien mieć staż: {2} lat")
    @MethodSource("seniorityProvider")
    void shouldCalculateCorrectSeniorityInYears(LocalDate hireDate, LocalDate referenceDate, int expectedYears) {
        // GIVEN
        Employee employee = new Employee("Jan", "Kowalski", "jan@k.pl", "Corp", Position.PROGRAMISTA, hireDate);

        // WHEN
        int actualYears = employee.getSeniorityInYears(referenceDate);

        // THEN
        // AssertJ
        assertThat(actualYears).isEqualTo(expectedYears);

        // Hamcrest (czytelne matchery liczbowe)
        assertThat(actualYears, is(expectedYears));
        assertThat(actualYears, greaterThanOrEqualTo(0));
    }
}
package com.test;

import com.test.model.Employee;
import com.test.model.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.test.service.EmployeeAnalytics;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

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

    @Test
    void shouldFilterEmployeesBySeniorityRange() {
        // GIVEN
        LocalDate now = LocalDate.now();
        Employee junior = new Employee("J", "Jun", "j@c.pl", "C", Position.STAZYSTA, now.minusYears(1));
        Employee mid = new Employee("M", "Mid", "m@c.pl", "C", Position.PROGRAMISTA, now.minusYears(3));
        Employee senior = new Employee("S", "Sen", "s@c.pl", "C", Position.MANAGER, now.minusYears(10));

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(junior);
        employees.add(mid);
        employees.add(senior);

        EmployeeAnalytics analytics = new EmployeeAnalytics(employees);

        // WHEN (szukamy pracowników ze stażem 2-5 lat)
        List<Employee> results = analytics.filterBySeniority(2, 5);

        // THEN
        // AssertJ - sprawdzamy czy znalazł tylko mida
        assertThat(results)
                .hasSize(1)
                .extracting(Employee::getSurname)
                .containsExactly("Mid");

        // Hamcrest
        assertThat(results, hasSize(1));
    }

    @Test
    void shouldFindJubilees() {
        // GIVEN
        LocalDate now = LocalDate.now();
        // Pracownik z 5-letnim stażem (Jubilat)
        Employee jubilat5 = new Employee("J", "5", "5@c.pl", "C", Position.PROGRAMISTA, now.minusYears(5));
        // Pracownik z 10-letnim stażem (Jubilat)
        Employee jubilat10 = new Employee("J", "10", "10@c.pl", "C", Position.PROGRAMISTA, now.minusYears(10));
        // Pracownik z 6-letnim stażem (Nie jubilat)
        Employee regular = new Employee("R", "6", "6@c.pl", "C", Position.PROGRAMISTA, now.minusYears(6));

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(jubilat5);
        employees.add(jubilat10);
        employees.add(regular);

        EmployeeAnalytics analytics = new EmployeeAnalytics(employees);

        // WHEN
        List<Employee> jubilates = analytics.findJubilees(5); // Szukamy wielokrotności 5 lat

        // THEN
        assertThat(jubilates)
                .hasSize(2)
                .extracting(Employee::getSurname)
                .containsExactlyInAnyOrder("5", "10");
    }
}
package com.test;

import com.test.exception.InvalidDataException;
import com.test.model.Employee;
import com.test.model.Position;
import com.test.model.ProjectTeam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple; // Import statyczny dla tuple!

public class ProjectTeamTest {

    private ProjectTeam teamAlpha;
    private Employee dev1, dev2, manager, stazysta;

    @BeforeEach
    void setUp() {
        // Zespół z limitem 3 osób
        teamAlpha = new ProjectTeam("Alpha", 3);

        dev1 = new Employee("Jan", "Kowalski", "jan@t.pl", "C", Position.PROGRAMISTA);
        dev2 = new Employee("Anna", "Nowak", "anna@t.pl", "C", Position.PROGRAMISTA);
        manager = new Employee("Piotr", "Szef", "szef@t.pl", "C", Position.MANAGER);
        stazysta = new Employee("Młody", "Wilk", "mlody@t.pl", "C", Position.STAZYSTA);
    }

    @Test
    void shouldAddMemberToTeam() {
        // WHEN
        teamAlpha.addMember(dev1);

        // THEN
        assertThat(teamAlpha.getMembers())
                .hasSize(1)
                .extracting(Employee::getName, Employee::getCurrentTeamName)
                .containsExactly(tuple("Jan", "Alpha"));
    }

    @Test
    void shouldThrowExceptionWhenTeamIsFull() {
        // GIVEN
        teamAlpha.addMember(dev1);
        teamAlpha.addMember(manager);
        teamAlpha.addMember(stazysta); // 3/3

        // WHEN & THEN
        assertThatThrownBy(() -> teamAlpha.addMember(dev2)) // 4 osoba
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Team is full");
    }

    @Test
    void shouldThrowExceptionWhenEmployeeAlreadyInTeam() {
        // GIVEN
        teamAlpha.addMember(dev1);
        ProjectTeam teamBeta = new ProjectTeam("Beta", 5);

        // WHEN & THEN (Próba dodania Jana do Beta bez usunięcia z Alpha)
        assertThatThrownBy(() -> teamBeta.addMember(dev1))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Employee is already assigned to team Alpha");
    }

    @Test
    void shouldTransferMemberBetweenTeams() {
        // GIVEN
        ProjectTeam teamBeta = new ProjectTeam("Beta", 5);
        teamAlpha.addMember(dev1);

        // WHEN
        teamAlpha.transferMember(dev1, teamBeta);

        // THEN
        // AssertJ - weryfikacja obu kolekcji jednocześnie
        assertThat(teamAlpha.getMembers()).isEmpty();

        assertThat(teamBeta.getMembers())
                .hasSize(1)
                .extracting(Employee::getName, Employee::getCurrentTeamName)
                .containsExactly(tuple("Jan", "Beta"));
    }

    // Źródło danych: Lista pracowników, wymagana liczba unikalnych ról, oczekiwany rezultat (true/false)
    static Stream<Arguments> diversityProvider() {
        return Stream.of(
                // 2 Programistów = 1 rola. Wymagane 2. Wynik: Fałsz
                Arguments.of(List.of(Position.PROGRAMISTA, Position.PROGRAMISTA), 2, false),
                // 1 Programista + 1 Manager = 2 role. Wymagane 2. Wynik: Prawda
                Arguments.of(List.of(Position.PROGRAMISTA, Position.MANAGER), 2, true),
                // 1 Programista. Wymagane 1. Wynik: Prawda
                Arguments.of(List.of(Position.PROGRAMISTA), 1, true)
        );
    }

    @ParameterizedTest(name = "Zespól z rolami {0} (min {1} unikalnych) -> Czy różnorodny? {2}")
    @MethodSource("diversityProvider")
    void shouldVerifyDiversityRequirements(List<Position> positions, int minDiversePositions, boolean expectedResult) {
        // GIVEN
        ProjectTeam team = new ProjectTeam("DiversityCheck", 10);
        int counter = 0;
        for (Position p : positions) {
            team.addMember(new Employee("User" + counter++, "Test", "u"+counter+"@t.pl", "C", p));
        }

        // WHEN
        boolean isDiverse = team.meetsDiversityRequirement(minDiversePositions);

        // THEN
        assertThat(isDiverse).isEqualTo(expectedResult);
    }
}
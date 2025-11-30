package com.test.service;

import com.test.model.Employee;
import com.test.model.Position;
import com.test.model.Employee;
import com.test.model.Position;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeAnalytics {
    private final List<Employee> employees;

    public EmployeeAnalytics(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> SortEmployeesBySurname() {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getSurname))
                .collect(Collectors.toList());
    }

    public Map<Position, List<Employee>> GroupEmployeesByPosition(){
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getJobTitle));
    }

    public Map<Position, Long> CountEmployeesByPosition(){
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getJobTitle, Collectors.counting()));
    }

    public double CalculateAverageSalary() {
        return employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
    }

    public Optional<Employee> GetHighestPaidEmployee() {
        return employees.stream()
                .max(Comparator.comparing(Employee::getSalary));
    }

    public List<Employee> filterBySeniority(int minYears, int maxYears) {
        LocalDate now = LocalDate.now();
        return employees.stream()
                .filter(e -> {
                    int seniority = e.getSeniorityInYears(now);
                    return seniority >= minYears && seniority <= maxYears;
                })
                .collect(Collectors.toList());
    }

    public List<Employee> findJubilees(int intervalYears) {
        LocalDate now = LocalDate.now();
        return employees.stream()
                .filter(e -> {
                    int seniority = e.getSeniorityInYears(now);
                    return seniority > 0 && seniority % intervalYears == 0;
                })
                .collect(Collectors.toList());
    }
}

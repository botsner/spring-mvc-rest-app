package ru.botsner.spring.rest.service;

import ru.botsner.spring.rest.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    void saveEmployee(Employee employee);

    Employee getEmployee(int id);

    void updateEmployee(Employee employee, int id);

    void deleteEmployee(int id);
}

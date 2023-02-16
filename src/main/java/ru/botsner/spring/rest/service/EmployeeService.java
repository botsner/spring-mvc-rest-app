package ru.botsner.spring.rest.service;

import ru.botsner.spring.rest.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    void saveEmployee(Employee employee);

    Employee getEmployee(int id);

    Employee updateEmployee(Employee employee, int id);

    Employee deleteEmployee(int id);
}

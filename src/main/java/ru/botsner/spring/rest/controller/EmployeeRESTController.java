package ru.botsner.spring.rest.controller;

import ru.botsner.spring.rest.entity.Employee;
import ru.botsner.spring.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRESTController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRESTController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> listAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{empId}")
    public Employee getEmployee(@PathVariable int empId) {
        return employeeService.getEmployee(empId);
    }

    @PostMapping
    public Employee addNewEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return employee;
    }

    @PutMapping("/{empId}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable int empId) {
        employeeService.updateEmployee(employee, empId);
        return employee;
    }

    @DeleteMapping("/{empId}")
    public void deleteEmployee(@PathVariable int empId) {
        employeeService.deleteEmployee(empId);
    }
}

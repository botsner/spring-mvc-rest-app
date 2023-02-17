package ru.botsner.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.botsner.spring.rest.entity.Employee;
import ru.botsner.spring.rest.exception_handling.EmployeeNotFoundException;
import ru.botsner.spring.rest.service.EmployeeService;

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
    public ResponseEntity<List<Employee>> listAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping("/{empId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int empId) {
        Employee employee = employeeService.getEmployee(empId);

        if (employee == null) {
            throw new EmployeeNotFoundException("There is no employee with ID = " + empId + " in Database");
        }
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{empId}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable int empId) {
        Employee updatedEmployee = employeeService.updateEmployee(employee, empId);
        return ResponseEntity.ok().body(updatedEmployee);
    }

    @DeleteMapping("/{empId}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable int empId) {
        Employee deletedEmp = employeeService.deleteEmployee(empId);

        if (deletedEmp == null) {
            throw new EmployeeNotFoundException("There is no employee with ID = " + empId + " in Database");
        }
        return ResponseEntity.ok().body(deletedEmp);
    }
}

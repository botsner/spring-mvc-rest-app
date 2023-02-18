package ru.botsner.spring.rest.exception_handling;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(int id) {
        super("Employee with ID = " + id + " not found");
    }
}

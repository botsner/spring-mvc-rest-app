package ru.botsner.spring.rest.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeTest {

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("Ivan", "Smirnov", "IT", 1000);
        employee.setId(5);
    }

    @Test
    void getId() {
        assertEquals(5, employee.getId());
    }

    @Test
    void setId() {
        employee.setId(10);
        assertEquals(10, employee.getId());
    }

    @Test
    void getName() {
        assertEquals("Ivan", employee.getName());
    }

    @Test
    void setName() {
        employee.setName("Elena");
        assertEquals("Elena", employee.getName());
    }

    @Test
    void getSurname() {
        assertEquals("Smirnov", employee.getSurname());
    }

    @Test
    void setSurname() {
        employee.setSurname("Petrova");
        assertEquals("Petrova", employee.getSurname());
    }

    @Test
    void getDepartment() {
        assertEquals("IT", employee.getDepartment());
    }

    @Test
    void setDepartment() {
        employee.setDepartment("HR");
        assertEquals("HR", employee.getDepartment());
    }

    @Test
    void getSalary() {
        assertEquals(1000, employee.getSalary());
    }

    @Test
    void setSalary() {
        employee.setSalary(2000);
        assertEquals(2000, employee.getSalary());
    }
}
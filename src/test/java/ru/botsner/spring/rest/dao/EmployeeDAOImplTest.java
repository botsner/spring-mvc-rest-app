package ru.botsner.spring.rest.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.botsner.spring.rest.entity.Employee;
import ru.botsner.spring.rest.testconfig.TestSpringConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringConfig.class)
@WebAppConfiguration
@Sql({"/employee-reset-autoincrement.sql", "/employee-test-data.sql"})
class EmployeeDAOImplTest {

    @Autowired
    private EmployeeDAO employeeDAO;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("Pavel", "Ivanov", "HR", 1000);
    }

    @Test
    @Transactional
    void daoLoads() {
        assertNotNull(employeeDAO);
    }

    @Test
    @Transactional
    void getAllEmployees() {
        assertEquals(3, employeeDAO.getAllEmployees().size());
    }

    @Test
    @Transactional
    void saveEmployee() {
        employeeDAO.saveEmployee(employee);
        assertTrue(employee.getId() > 0);
    }

    @Test
    @Transactional
    void updateEmployee() {
        employee.setId(1);
        employeeDAO.saveEmployee(employee);

        Employee updatedEmp = employeeDAO.getEmployee(1);
        assertEquals(1, updatedEmp.getId());
        assertEquals("Pavel", updatedEmp.getName());
        assertEquals("Ivanov", updatedEmp.getSurname());
        assertEquals("HR", updatedEmp.getDepartment());
        assertEquals(1000, updatedEmp.getSalary());
    }

    @Test
    @Transactional
    void getEmployee() {
        Employee emp = employeeDAO.getEmployee(1);
        assertEquals(1, emp.getId());
        assertEquals("Ivan", emp.getName());
        assertEquals("Petrov", emp.getSurname());
        assertEquals("IT", emp.getDepartment());
        assertEquals(1500, emp.getSalary());
    }

    @Test
    @Transactional
    void getEmployee_getNotExistingEmployee_null() {
        Employee emp = employeeDAO.getEmployee(99);
        assertNull(emp);
    }

    @Test
    @Transactional
    void deleteEmployee() {
        List<Employee> emps = employeeDAO.getAllEmployees();
        assertEquals(3, emps.size());
        employeeDAO.deleteEmployee(1);
        emps = employeeDAO.getAllEmployees();
        assertEquals(2, emps.size());
    }
}
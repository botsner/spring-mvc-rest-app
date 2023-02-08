package ru.botsner.spring.rest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.botsner.spring.rest.dao.EmployeeDAO;
import ru.botsner.spring.rest.entity.Employee;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    private EmployeeService service;
    @Mock
    private EmployeeDAO employeeDAO;

    private Employee employee;

    @BeforeEach
    void setUp() {
        service = new EmployeeServiceImpl(employeeDAO);
        employee = new Employee("Fedor", "Sumkin", "IT", 1000);
    }

    @Test
    void getAllEmployees() {
        Employee emp1 = new Employee("Givi", "Zurabovich", "HR", 1000);
        Employee emp2 = new Employee("Merin", "Gek", "IT", 1200);

        Mockito.doReturn(Arrays.asList(emp1, emp2)).when(employeeDAO).getAllEmployees();

        assertIterableEquals(Arrays.asList(emp1, emp2), service.getAllEmployees());
        Mockito.verify(employeeDAO, Mockito.only()).getAllEmployees();
    }

    @Test
    void saveEmployee() {
        service.saveEmployee(employee);
        Mockito.verify(employeeDAO, Mockito.only()).saveEmployee(employee);
    }

    @Test
    void getEmployee() {
        employee.setId(1);
        Mockito.doReturn(employee).when(employeeDAO).getEmployee(1);
        assertSame(employee, service.getEmployee(1));
        Mockito.verify(employeeDAO, Mockito.only()).getEmployee(1);
    }

    @Test
    void updateEmployee() {
        service.updateEmployee(employee, 1);
        assertEquals(1, employee.getId());
        Mockito.verify(employeeDAO, Mockito.only()).saveEmployee(employee);
    }

    @Test
    void deleteEmployee() {
        service.deleteEmployee(1);
        Mockito.verify(employeeDAO, Mockito.only()).deleteEmployee(1);
    }
}
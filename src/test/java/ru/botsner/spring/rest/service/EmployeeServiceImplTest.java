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
        Employee updatedEmp = new Employee("Merin", "Gek", "HR", 1200);
        employee.setId(1);
        Mockito.doReturn(employee).when(employeeDAO).getEmployee(1);

        service.updateEmployee(updatedEmp, 1);

        assertEquals(1, employee.getId());
        assertEquals("Merin", employee.getName());
        assertEquals("HR", employee.getDepartment());
        assertEquals(1200, employee.getSalary());

        Mockito.verify(employeeDAO, Mockito.only()).getEmployee(1);
    }

    @Test
    void updateEmployee_updatedEmployeeNotFoundInDB_null() {
        Mockito.doReturn(null).when(employeeDAO).getEmployee(1);

        assertNull(service.updateEmployee(employee, 1));

        Mockito.verify(employeeDAO, Mockito.only()).getEmployee(1);
    }

    @Test
    void deleteEmployee() {
        employee.setId(1);
        Mockito.doReturn(employee).when(employeeDAO).getEmployee(1);

        assertSame(employee, service.deleteEmployee(1));

        Mockito.verify(employeeDAO, Mockito.times(1)).getEmployee(1);
        Mockito.verify(employeeDAO, Mockito.times(1)).deleteEmployee(1);
    }

    @Test
    void deleteEmployee_deletedEmployeeNotFoundInDB_null() {
        Mockito.doReturn(null).when(employeeDAO).getEmployee(1);

        assertNull(service.deleteEmployee(1));

        Mockito.verify(employeeDAO, Mockito.only()).getEmployee(1);
    }
}
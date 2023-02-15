package ru.botsner.spring.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.botsner.spring.rest.entity.Employee;
import ru.botsner.spring.rest.exception_handling.EmployeeExceptionHandler;
import ru.botsner.spring.rest.exception_handling.EmployeeNotFoundException;
import ru.botsner.spring.rest.service.EmployeeService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeRESTControllerTest {

    private MockMvc mockMvc;
    @Mock
    private EmployeeService employeeService;

    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new EmployeeRESTController(employeeService))
                .setControllerAdvice(EmployeeExceptionHandler.class)
                .build();

        objectMapper = new ObjectMapper();

        employee = new Employee("Fedor", "Sumkin", "IT", 1000);
    }

    @Test
    void mockMvcLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    void listAllEmployees() throws Exception {
        Employee emp1 = new Employee("Givi", "Zurabovich", "HR", 1000);
        Employee emp2 = new Employee("Merin", "Gek", "IT", 1200);

        Mockito.doReturn(Arrays.asList(emp1, emp2)).when(employeeService).getAllEmployees();

        mockMvc.perform(
                get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(emp1, emp2))));

        Mockito.verify(employeeService, Mockito.only()).getAllEmployees();
    }

    @Test
    void getEmployee() throws Exception {
        employee.setId(1);

        Mockito.doReturn(employee).when(employeeService).getEmployee(Mockito.anyInt());

        mockMvc.perform(
                get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fedor"))
                .andExpect(jsonPath("$.surname").value("Sumkin"))
                .andExpect(jsonPath("$.department").value("IT"))
                .andExpect(jsonPath("$.salary").value(1000));

        Mockito.verify(employeeService, Mockito.only()).getEmployee(Mockito.anyInt());
    }

    @Test
    void getEmployee_getNotExistingEmployee_status404andExceptionThrown() throws Exception {
        Mockito.doReturn(null).when(employeeService).getEmployee(Mockito.anyInt());

        mockMvc.perform(
                get("/api/employees/1"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof EmployeeNotFoundException));
    }

    @Test
    void addNewEmployee() throws Exception {
        mockMvc.perform(
                post("/api/employees")
                        .content(objectMapper.writeValueAsString(employee))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employee)));

        Mockito.verify(employeeService, Mockito.only()).saveEmployee(Mockito.any(Employee.class));
    }


    @Test
    void updateEmployee() throws Exception {
        Mockito.doAnswer(invocation -> {
            invocation.getArgument(0, Employee.class).setId(1);
            return null;
        }).when(employeeService).updateEmployee(Mockito.any(Employee.class), Mockito.anyInt());

        mockMvc.perform(
                put("/api/employees/1")
                        .content(objectMapper.writeValueAsString(employee))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fedor"))
                .andExpect(jsonPath("$.surname").value("Sumkin"));

        Mockito.verify(employeeService, Mockito.only()).updateEmployee(Mockito.any(Employee.class), Mockito.anyInt());
    }

    @Test
    void deleteEmployee() throws Exception {
        Mockito.doReturn(employee).when(employeeService).getEmployee(Mockito.anyInt());

        mockMvc.perform(
                delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee with ID = 1 was deleted"));

        Mockito.verify(employeeService, Mockito.times(1)).getEmployee(Mockito.anyInt());
        Mockito.verify(employeeService, Mockito.times(1)).deleteEmployee(Mockito.anyInt());
    }

    @Test
    void deleteEmployee_deleteNotExistingEmployee_status404andExceptionThrown() throws Exception {
        Mockito.doReturn(null).when(employeeService).getEmployee(Mockito.anyInt());

        mockMvc.perform(
                delete("/api/employees/1"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof EmployeeNotFoundException));
    }
}
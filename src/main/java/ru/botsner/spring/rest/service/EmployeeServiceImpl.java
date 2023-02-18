package ru.botsner.spring.rest.service;

import ru.botsner.spring.rest.dao.EmployeeDAO;
import ru.botsner.spring.rest.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        employeeDAO.saveEmployee(employee);
    }

    @Override
    @Transactional
    public Employee getEmployee(int id) {
        return employeeDAO.getEmployee(id);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee, int id) {
        Employee updatedEmp = employeeDAO.getEmployee(id);
        if (updatedEmp != null) {
            updatedEmp.setName(employee.getName());
            updatedEmp.setSurname(employee.getSurname());
            updatedEmp.setDepartment(employee.getDepartment());
            updatedEmp.setSalary(employee.getSalary());
        }
        return updatedEmp;
    }

    @Override
    @Transactional
    public Employee deleteEmployee(int id) {
        Employee deletedEmp = employeeDAO.getEmployee(id);
        if (deletedEmp != null) {
            employeeDAO.deleteEmployee(id);
        }
        return deletedEmp;
    }
}

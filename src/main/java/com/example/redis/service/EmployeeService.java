package com.example.redis.service;

import com.example.redis.entity.Employee;
import com.example.redis.payload.EmployeeDto;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    public Employee saveEmployee(EmployeeDto employeeDto);
    public Page<Employee> getPaginatedAndSortingEmployee(int pageNumber, int pageSize, String sortBy, String sortDir);
    public Employee getEmployeeById(Long id);
}

package com.example.redis.service;

import com.example.redis.entity.Employee;
import com.example.redis.payload.EmployeeDto;
import com.example.redis.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String EMPLOYEE_CACHE_KEY_PREFIX = "employee:";

    @Override
    public Employee saveEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPhone(employeeDto.getPhone());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(employeeDto.getPassword());
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdateAt(LocalDateTime.now());
        employee.setEnable(true);
        return employeeRepo.save(employee);
    }

    @Override
    public Page<Employee> getPaginatedAndSortingEmployee(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return employeeRepo.findAll(pageable);
    }

    @Override
    public Employee getEmployeeById(Long id){
        String cacheKey = EMPLOYEE_CACHE_KEY_PREFIX + id;
        Employee cachedEmployee = (Employee) redisTemplate.opsForHash().get(EMPLOYEE_CACHE_KEY_PREFIX, cacheKey);
        if(cachedEmployee != null){
            return cachedEmployee;
        }
        Optional<Employee> employee = employeeRepo.findById(id);
        if(employee.isPresent()){
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
            redisTemplate.opsForHash().put(EMPLOYEE_CACHE_KEY_PREFIX, cacheKey, employee.get());
            return employee.get();
        }
        throw new RuntimeException("employee not found with id: " + id);
    }
}

package com.example.redis.controller;

import com.example.redis.entity.Employee;
import com.example.redis.payload.EmployeeDto;
import com.example.redis.service.EmployeeService;
import com.example.redis.utils.ApiResponse;
import com.example.redis.utils.ErrorResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        try{
            ApiResponse<Employee> response = new ApiResponse<>();
            Employee employee = employeeService.saveEmployee(employeeDto);
            response.setTimesTemp(LocalDateTime.now());
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setStatus("success");
            response.setMessage("New employee register successfully");
            response.setData(employee);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse();
            response.setTimesTemp(LocalDateTime.now());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatus("error");
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public  ResponseEntity<?> getEmployee(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "name") String sortBy,
                                          @RequestParam(defaultValue = "asc") String sortDir){
        try{
            ApiResponse<Page<Employee>> response = new ApiResponse<>();
            Page<Employee> employeePage = employeeService.getPaginatedAndSortingEmployee(page, size, sortBy, sortDir);
            response.setTimesTemp(LocalDateTime.now());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setStatus("success");
            response.setMessage("employee found");
            response.setData(employeePage);
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse();
            response.setTimesTemp(LocalDateTime.now());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatus("error");
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public  ResponseEntity<?> getEmployee(@PathVariable("id") long id){
        try{
            ApiResponse<Employee> response = new ApiResponse<>();
            Employee employee = employeeService.getEmployeeById(id);
            response.setTimesTemp(LocalDateTime.now());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setStatus("success");
            response.setMessage("employee found");
            response.setData(employee);
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse();
            response.setTimesTemp(LocalDateTime.now());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatus("error");
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

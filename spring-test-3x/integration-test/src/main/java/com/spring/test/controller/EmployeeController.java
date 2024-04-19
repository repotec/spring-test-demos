package com.spring.test.controller;


import com.spring.test.model.Employee;
import com.spring.test.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CURD Rest APIs for employee")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Operation(summary = "retrieves all employees REST API", description = "all employees REST API in hr system", method = "GET")
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<List<Employee>>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @Operation(summary = "create new employee REST API", description = "create new employee in hr system")
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Parameter(description = "Employee object") @RequestBody Employee employee) {
        return new ResponseEntity<Employee> (employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }
}
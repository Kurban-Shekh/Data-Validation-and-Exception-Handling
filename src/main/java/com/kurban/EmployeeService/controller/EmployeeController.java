package com.kurban.EmployeeService.controller;

import com.kurban.EmployeeService.dto.EmployeeDTO;
import com.kurban.EmployeeService.entity.Employee;
import com.kurban.EmployeeService.exception.UserNotFoundException;
import com.kurban.EmployeeService.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service){
        this.service = service;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<Employee>> fetchAllEmployees(){
        List<Employee> employees = service.getAllEmployees();
        if(employees.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(employees);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> fetchEmployee(@PathVariable int id) throws UserNotFoundException {
        return ResponseEntity.ok(service.getEmployeeById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(@Valid @RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveEmployee(employeeDTO));
    }
}

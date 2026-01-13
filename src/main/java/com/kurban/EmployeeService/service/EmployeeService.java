package com.kurban.EmployeeService.service;

import com.kurban.EmployeeService.dto.EmployeeDTO;
import com.kurban.EmployeeService.entity.Employee;
import com.kurban.EmployeeService.exception.UserNotFoundException;
import com.kurban.EmployeeService.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAllEmployees(){
        return repository.findAll();
    }

    public Employee getEmployeeById(int id) throws  UserNotFoundException{
        Employee employee = repository.findById(id).orElse(null);
        if(employee==null){
            throw new UserNotFoundException("The user with user id "+ id + " is not present." );
        }else{
            return employee;
        }
    }

    private Employee ConvertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setSalary(employeeDTO.getSalary());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhone(employeeDTO.getPhone());
        employee.setLocation(employeeDTO.getLocation());
        return employee;
    }

    public Employee saveEmployee(EmployeeDTO employee){
        return repository.save(ConvertEmployeeDTOToEmployee(employee));
    }
}

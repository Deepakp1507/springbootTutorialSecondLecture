package com.example.springbootTutorial.controller;

import com.example.springbootTutorial.DTO.EmployeeDTO;
import com.example.springbootTutorial.entities.EmployeeEntity;
import com.example.springbootTutorial.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (path = "/employees")
public class EmplpyeeController {

    private  final EmployeeService employeeService;

    public EmplpyeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id){
        return employeeService.getEmployeeById(id);

    }
    @GetMapping
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false, name = "inputAge") Integer age,
                                             @RequestParam(required = false) String sortBy) {
        return employeeService.getAllEmployee();
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputDto){
        return  employeeService.createNewEmployee(inputDto);
    }



}

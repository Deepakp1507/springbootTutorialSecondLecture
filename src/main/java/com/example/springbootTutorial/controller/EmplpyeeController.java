package com.example.springbootTutorial.controller;

import com.example.springbootTutorial.DTO.EmployeeDTO;
import com.example.springbootTutorial.entities.EmployeeEntity;
import com.example.springbootTutorial.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping (path = "/employees")
public class EmplpyeeController {

    private  final EmployeeService employeeService;

    public EmplpyeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long id){
        Optional<EmployeeDTO> employeeDTO= employeeService.getEmployeeById(id);
        ResponseEntity<EmployeeDTO> employeeDTOResponseEntity = employeeDTO.map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1)).orElse(ResponseEntity.notFound().build());
        return employeeDTOResponseEntity;
    }
    @GetMapping
    public ResponseEntity<List<EmployeeDTO> >getAllEmployees(@RequestParam(required = false, name = "inputAge") Integer age,
                                             @RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeDTO inputDto){
        EmployeeDTO savedEmployee=  employeeService.createNewEmployee(inputDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path ="/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO inputDTO, @PathVariable Long employeeId){
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId, inputDTO));
    }

    @DeleteMapping (path ="/{employeeId}")
    public  ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId){
        boolean isDeleted= employeeService.deleteEmployeeById(employeeId);
        if(isDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping (path ="/{employeeId}")
    public  ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId){
        EmployeeDTO employeeDTO=  employeeService.updatePartialEmployeeById(updates,employeeId);
        if(employeeDTO== null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(employeeDTO);
    }


}

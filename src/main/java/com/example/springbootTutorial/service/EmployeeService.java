package com.example.springbootTutorial.service;

import com.example.springbootTutorial.DTO.EmployeeDTO;
import com.example.springbootTutorial.entities.EmployeeEntity;
import com.example.springbootTutorial.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private  final EmployeeRepository employeeRepository;
    private  final ModelMapper modelMapper;
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployeeById(Long id){
        EmployeeEntity employeeEntity= (EmployeeEntity) employeeRepository.findById(id).orElse(null);

        return modelMapper.map(employeeEntity, EmployeeDTO.class);

    }


    public List<EmployeeDTO> getAllEmployee() {

        List<EmployeeEntity> employeeEntity =  employeeRepository.findAll();
       return  employeeEntity.stream().map(employee->modelMapper.map(employee,EmployeeDTO.class))
                .collect(Collectors.toList());

    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputDto) {
       EmployeeEntity toSaveEntity=  modelMapper.map(inputDto,EmployeeEntity.class);
       EmployeeEntity createdEntity = employeeRepository.save(toSaveEntity);
       return modelMapper.map(createdEntity,EmployeeDTO.class);

    }
}

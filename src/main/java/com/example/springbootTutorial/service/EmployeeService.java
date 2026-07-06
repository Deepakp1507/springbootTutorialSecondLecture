package com.example.springbootTutorial.service;

import com.example.springbootTutorial.DTO.EmployeeDTO;
import com.example.springbootTutorial.entities.EmployeeEntity;
import com.example.springbootTutorial.repositories.EmployeeRepository;
import org.apache.el.util.ReflectionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO inputDTO) {
       EmployeeEntity input=  modelMapper.map(inputDTO, EmployeeEntity.class);
       input.setId(employeeId);
        EmployeeEntity savedEmployeeEntity =  employeeRepository.save(input);
        return  modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);

    }

    public boolean deleteEmployeeById(Long employeeId) {

            boolean exist = isExistByID(employeeId);
            if(!exist){
                System.out.println("Not Found");
                return false;
            }

            employeeRepository.deleteById(employeeId);
        System.out.println(" Found");
            return  true;

    }
public  boolean isExistByID(Long employeeID){
    boolean exist = employeeRepository.existsById(employeeID);
    return  exist;
}
    public EmployeeDTO updatePartialEmployeeById(Map<String, Object> updates, Long employeeId) {
        boolean exist = isExistByID(employeeId);
        if(!exist) return null;

        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElse(null);
        updates.forEach((field,value)->{
            Field fieldTobeUpdated = ReflectionUtils.findField(EmployeeEntity.class,field);
            fieldTobeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldTobeUpdated, employeeEntity,value);
        });
     employeeRepository.save(employeeEntity);
     return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }
}

package com.example.springbootTutorial.exceptions;

public class ResourceNotFoundException extends  RuntimeException{

    public  ResourceNotFoundException(String message){
        super(message);
    }
}

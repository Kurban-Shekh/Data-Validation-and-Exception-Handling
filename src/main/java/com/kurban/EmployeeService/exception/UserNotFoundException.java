package com.kurban.EmployeeService.exception;

public class UserNotFoundException extends  Exception{
    public UserNotFoundException(String message){
        super(message);
    }
}

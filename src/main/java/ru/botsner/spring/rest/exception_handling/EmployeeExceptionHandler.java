package ru.botsner.spring.rest.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handleException(EmployeeNotFoundException exception) {
        EmployeeIncorrectData incorrectData = new EmployeeIncorrectData();
        incorrectData.setInfo(exception.getMessage());

        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }
}

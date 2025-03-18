package com.coder.springjwt.globalExceptionHandler;

import com.coder.springjwt.exception.customerPanelException.InvalidMobileNumberException;
import com.coder.springjwt.exception.customerPanelException.InvalidUsernameAndPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandlers {

    @ExceptionHandler(InvalidMobileNumberException.class)
    public ResponseEntity<?> invalidMobileNumberException(InvalidMobileNumberException invalidMobileNumberException)
    {
        String message = invalidMobileNumberException.getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(message);
        exceptionResponse.setDateTime(LocalDateTime.now());
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(InvalidUsernameAndPasswordException.class)
    public ResponseEntity<?> invalidUsernameAndPasswordException(InvalidUsernameAndPasswordException invalidUsernameAndPasswordException)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(invalidUsernameAndPasswordException.getMessage());
        exceptionResponse.setDateTime(LocalDateTime.now());
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(AddressLimitExceededException.class)
    public ResponseEntity<?> AddressLimitExceededException(AddressLimitExceededException addressLimitExceededException)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(addressLimitExceededException.getMessage());
        exceptionResponse.setDateTime(LocalDateTime.now());
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }
}

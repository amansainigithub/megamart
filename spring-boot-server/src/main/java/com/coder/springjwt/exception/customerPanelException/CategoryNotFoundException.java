package com.coder.springjwt.exception.customerException;

public class CategoryNotFoundException  extends RuntimeException{

    public  CategoryNotFoundException(String message){
        super(message);
    }
}

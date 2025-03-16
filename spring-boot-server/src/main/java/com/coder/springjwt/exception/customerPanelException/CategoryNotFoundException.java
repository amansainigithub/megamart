package com.coder.springjwt.exception.customerPanelException;

public class CategoryNotFoundException  extends RuntimeException{

    public  CategoryNotFoundException(String message){
        super(message);
    }
}

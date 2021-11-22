package com.cliff.cliffbackend1.exceptions;

/**
 * Parameters needed to configure the object
 * {@param message}
 * {@param statusCode}
 * This class is the parent class for the Custom exceptions
 * */
public class CustomException extends Exception{
    private final String message;
    private final Integer statusCode;

    public CustomException(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage(){
        return this.message;
    }

    public String getLocalizedMessage(){
        return "Exception: "+this.message+" - with the status Code: "+this.statusCode;
    }

    public Integer getStatusCode(){
        return this.statusCode;
    }
}

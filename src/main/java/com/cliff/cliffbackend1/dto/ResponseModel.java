package com.cliff.cliffbackend1.dto;

/**
 * Basic POJO class that id used as a data transfer object
 * uses {@link javax.validation} for validating the requests and responses
 * */
public class ResponseModel<T> {
    private String result;
    private String message;
    private T data;

    public ResponseModel(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public ResponseModel(String result, String message) {
        this.result = result;
        this.message = message;
        this.data = null;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

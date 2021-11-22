package com.cliff.cliffbackend1.exceptions;

import com.cliff.cliffbackend1.dto.ResponseModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * This class basically maps the exceptions to custom responses
 * controls all exceptions thrown by
 * {@link com.cliff.cliffbackend1.controllers} classes methods
 * */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ResponseModel<List<String>> response = new ResponseModel<>("failed", "Arguments not sent properly", errors);
        return ResponseEntity.status(400).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        assert message != null;
        message = message.substring(0, message.indexOf(':'));
        ResponseModel<String> response = new ResponseModel<>(
                "failed", message);
        return ResponseEntity.status(501).body(response);
    }

    @ExceptionHandler({CustomException.class, ResourceAlreadyExistsException.class, ResourceNotFoundException.class})
    public ResponseEntity<Object> handleCustom(CustomException ex, WebRequest request) {
        ResponseModel<String> response = new ResponseModel<>(
                "failed", ex.getLocalizedMessage(), "error occurred");
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        String message = ex.getLocalizedMessage();
        assert message != null;
        message = message.substring(0, message.indexOf(':'));
        ResponseModel<String> response = new ResponseModel<>(
                "failed", ex.getMessage(), "error occurred");
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleNullPointer(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ResponseModel<String> response = new ResponseModel<>(
                "failed", ex.toString(), "error occurred");
        return ResponseEntity.status(500).body(response);
    }
}

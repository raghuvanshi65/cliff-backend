package com.cliff.cliffbackend1.exceptions;

/**
 * Parameters needed to configure the object
 * {@param message}
 * {@param statusCode}
 * {@param className}
 * Please check {@link com.cliff.cliffbackend1.exceptions.CustomException for the parent class}
 * This Custom Exception is thrown when an entity is not found in the database
 */
public class ResourceNotFoundException extends CustomException {
    private final Class<?> className;

    public ResourceNotFoundException(String message, Integer statusCode, Class<?> className) {
        super(message, statusCode);
        this.className = className;
    }

    public Class<?> getClassInfo() {
        return this.className;
    }

    public String getClassName() {
        return this.className.toGenericString();
    }
}

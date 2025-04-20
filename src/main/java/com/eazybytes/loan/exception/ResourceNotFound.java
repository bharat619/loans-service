package com.eazybytes.loan.exception;

public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}

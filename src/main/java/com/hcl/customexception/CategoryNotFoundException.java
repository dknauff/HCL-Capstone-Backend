package com.hcl.customexception;

public class CategoryNotFoundException extends RuntimeException {
public CategoryNotFoundException(String message) {
	super(message);
}
}

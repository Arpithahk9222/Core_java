package com.hm.UserMicroservice.Exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(Long id) {
		super(" User is not found with this id  "+id);
	 
	}
 
}

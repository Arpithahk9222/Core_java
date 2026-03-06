package com.hm.UserMicroservice.service;

import org.springframework.data.domain.Pageable;

import java.util.List;
 

import com.hm.UserMicroservice.DTO.UserDTO;
import com.hm.UserMicroservice.DTO.UserPatchDTO;
 
import com.hm.UserMicroservice.entity.User;

import jakarta.validation.Valid;

public interface UserService  {

	 

 List<User> getAllUsers();

 User createUserFromDTO(UserDTO userDto);

 User getUserById(Long id);

 User modifyalldata(@Valid UserDTO userRequest, Long id);

 User updateUser(UserPatchDTO userreq, Long id);

 List<User> getAlluserbypages(Pageable pageable);
 
 
}

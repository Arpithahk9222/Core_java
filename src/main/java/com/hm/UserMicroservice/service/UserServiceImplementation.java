package com.hm.UserMicroservice.service;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

 
import org.springframework.stereotype.Service;

import com.hm.UserMicroservice.DTO.UserDTO;
import com.hm.UserMicroservice.DTO.UserPatchDTO;
import com.hm.UserMicroservice.Exception.DuplicateResourceException;
import com.hm.UserMicroservice.Exception.UserNotFoundException;
import com.hm.UserMicroservice.entity.User;
import com.hm.UserMicroservice.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor  // to create constructor for DI
@Service
public class UserServiceImplementation implements UserService {
	
	private final  UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

//	
//	public UserServiceImplementation(UserRepository userRepository) {
//		this.userRepository=userRepository;
//	}



	@Override
	public User createUserFromDTO(UserDTO userDto) {
		if(userRepository.existsByEmail(userDto.getEmail())) {
			throw new DuplicateResourceException ("Email Already exists");
		}
		User user=new User();
				user.setName(userDto.getName());
				user.setEmail(userDto.getEmail());
				user.setAge(userDto.getAge());
				user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		return userRepository.save(user);
	}
	 

	@Override
	public List<User> getAllUsers() {
		 
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		Optional<User> foundUser=userRepository.findById(id); 
		if(foundUser.isEmpty()) {
			throw new UserNotFoundException(id);
		}
		return foundUser.get();
		
	}

	@Override
	public User modifyalldata(@Valid UserDTO userRequest, Long id) {
		
		Optional<User> userFound=userRepository.findById(id);
		if(userFound.isEmpty()) {
			throw new UserNotFoundException(id);
		}
		if(userRepository.existsByEmail(userRequest.getEmail())) {
			throw new DuplicateResourceException("email already exists");
		}
		User user=userFound.get();
		user.setEmail(userRequest.getEmail());
		user.setName(userRequest.getName());
		user.setAge(userRequest.getAge());
		 
		return userRepository.save(user);
	}

	@Override
	public User updateUser(UserPatchDTO userreq, Long id) {
		 Optional<User> foundUser=userRepository.findById(id);
		 if(foundUser.isEmpty()) {
			 throw new UserNotFoundException(id);
			 }
		
		 User user=foundUser.get();
		 if(userreq.getAge() != null) {
			 if(userreq.getAge()<18) {
				 throw new IllegalArgumentException("Age should be above 18");
			 }
			 user.setAge(userreq.getAge());
		 }
		 
		 if(userreq.getEmail()!=null) {
			 
			 if(!userreq.getEmail() .contains("@")) {
				 throw new IllegalArgumentException("Email should be in proper format ex.john@gmail.com");
			 }
			 if(userRepository.existsByEmail(userreq.getEmail())) {
					throw new DuplicateResourceException("email already exists");
				}
			 user.setEmail(userreq.getEmail());
		 }
		 
		 if(userreq.getName()!=null) {
			 if(userreq.getName().isBlank()) {
				 throw new IllegalArgumentException("name should not be empty");
			 }
			 user.setName(userreq.getName());
		 }
		return userRepository.save(user);
	}

	@Override
	public List<User> getAlluserbypages(Pageable pageable) {
	 
		return userRepository.findAll(pageable).getContent();
	}
 
}

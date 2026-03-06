package com.hm.UserMicroservice.Controllers;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.http.HttpStatus;
 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.UserMicroservice.DTO.UserDTO;
import com.hm.UserMicroservice.DTO.UserPatchDTO;
import com.hm.UserMicroservice.entity.User;
import com.hm.UserMicroservice.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor // to create constructor for DI
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	/*
	 * This DI doesn't require Autowire annotaion .If a class has only ONE
	 * constructor, Spring automatically injects dependencies. Constructor injection
	 * is preferred because it enforces required dependencies, enables immutability,
	 * improves testability, and prevents partially initialized beans.
	 */
	/*
	 * | Feature | Constructor | Setter | Field | | ---------------------- |
	 * ----------- | ---------- | ------ | | Mandatory dependency | ✅ Yes | ❌ No | ❌
	 * No | | Immutability (`final`) | ✅ Yes | ❌ No | ❌ No | | Fail-fast | ✅ Yes |
	 * ⚠️ Partial | ❌ No | | Testability | ✅ Easy | ⚠️ Medium | ❌ Hard | |
	 * Readability | ✅ Clear | ⚠️ Medium | ❌ Poor | | Spring recommendation | ⭐⭐⭐⭐⭐
	 * | ⭐⭐ | ❌ | | Production ready | ✅ YES | ⚠️ Maybe | ❌ NO |
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	private final UserService userService;

	/*
	 * public UserController(UserService userService) { this.userService =
	 * userService; //if @RequiredArgs annotation not used then this constructor
	 * injection }
	 */

	/*
	 * “Hibernate performs UPDATE because the entity had a non-null ID. The save()
	 * method decides based on entity state, not HTTP method.”
	 * 
	 * Shorter:
	 * 
	 * “POST does not guarantee INSERT; entity ID does.
	 */
	/*
	 * Avoid using Ids in post method to avoid data corruption by hiberante
	 * 
	 * @PostMapping User createUser(@RequestBody User newUser) {
	 * System.out.println(newUser.getName()); return
	 * userService.createUser(newUser); }
	 */

	// =========================
	// Post USERS
	// =========================
	@PostMapping // dON'T PASS ID FOR POST REQUEST
	ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUserFromDTO(userDto));

	}

	// =========================
	// GET ALL USERS
	// =========================
	@GetMapping
	ResponseEntity<List<User>> getAllUser() {
		userService.getAllUsers().stream().forEach(System.out::println);
		return ResponseEntity.ok(userService.getAllUsers());

	}
	
	@GetMapping("/paged")
	public ResponseEntity<List<User>> getUsersbyPage(Pageable pageable){
		return ResponseEntity.ok( userService.getAlluserbypages(pageable));
	}

	
	@GetMapping("/{id}")
	ResponseEntity<User> getUserById(@PathVariable("id") Long id){
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	/*
	 * “PUT validates all fields because it represents a complete replacement of a
	 * resource, while PATCH validates only provided fields because missing fields
	 * are intentional.”
	 * 
	 */

	// =========================
	// PUT USERS
	// =========================
	
	@PutMapping("/{id}")
	public ResponseEntity<User> modifyeveryData(@Valid @RequestBody  UserDTO userRequest ,@PathVariable("id") Long id) {
		return ResponseEntity.ok(userService.modifyalldata(userRequest,id));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<User>modifydata(@RequestBody UserPatchDTO userreq,@PathVariable("id") Long id){
		//System.out.println(">>> PATCH SERVICE ENTERED");
		return ResponseEntity.ok(userService.updateUser(userreq,id));
	}
	
}

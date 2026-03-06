package com.hm.UserMicroservice.DTO;

 

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
 @AllArgsConstructor
public class UserDTO {


	 
	@Column(nullable = false)
	@NotBlank(message = "name should not be blank")
    private String name;

    @Email(message = "Email should be in proper format ex.john@gmail.com")
    @NonNull
    @Column(unique = true)
    private String email;
    
    @Min(value = 18 ,message = "minimum age should be 18 and above ")
    private int age;

    
    private String password;  


 

}

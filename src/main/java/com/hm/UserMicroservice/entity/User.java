package com.hm.UserMicroservice.entity;

import jakarta.persistence.Entity;

 
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users",  uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
    })
public class User {
		
	@Id
	@GeneratedValue
	    private Long id;

	 
	 private String name;

	    
	   
	   @Column(unique = true)
private String email;

	    private Integer age;
	 
		private String password;
 //getters setters
		 
}

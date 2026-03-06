package com.hm.UserMicroservice.repository;

import java.awt.print.Pageable;import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hm.UserMicroservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User getUserById(Long id);

	boolean existsByEmail(String email);

     Optional<User> findByEmail(String username);



}

package com.example.spring_security_login_register.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_security_login_register.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

}

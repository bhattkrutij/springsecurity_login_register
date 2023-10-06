package com.example.spring_security_login_register.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.spring_security_login_register.entities.User;
import com.example.spring_security_login_register.repositories.UserRepository;
import com.example.spring_security_login_register.services.UserService;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User saveUser(User user) {
		System.err.println("================="+user.getEmail()+user.getName()+user.getPassword()+user.getRole());
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		user.setRole("ROLE_USER");
		return userRepository.save(user);
	}


	@Override
	public void removeSessionMsg() {
		
			HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes()))
					.getRequest().getSession();
			session.removeAttribute("msg");

		
	}


	@Override
	public User findUserById(Long id) {
	Optional<User> user = userRepository.findById(id);
	if(user.isPresent()) {
		return user.get();
	}else {
		throw new NullPointerException("user not found");
	}
		
	}


	@Override
	public boolean deleteUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			userRepository.delete(user.get());
			return true;
		}else {
			return false;
		}
		
	}

}

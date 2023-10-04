package com.example.spring_security_login_register.controller;

import java.security.Principal;
import org.springframework.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.spring_security_login_register.entities.User;
import com.example.spring_security_login_register.repositories.UserRepository;
import com.example.spring_security_login_register.services.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeContoller {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@ModelAttribute
	public void commonUser(Model m, Principal p) {
		if (p != null) {
			String email = p.getName();
			User user = userRepository.findByEmail(email);
			m.addAttribute("user", user);
		}
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session) {
		User u = userService.saveUser(user);
		if (u != null) {
			session.setAttribute("msg", "Register successfully");

		} else {
			session.setAttribute("msg", "Something wrong server");
		}
		return "redirect:/register";
	}

}

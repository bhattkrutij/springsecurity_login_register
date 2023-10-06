package com.example.spring_security_login_register.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part.IgnoreCaseType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.spring_security_login_register.entities.User;
import com.example.spring_security_login_register.repositories.UserRepository;
import com.example.spring_security_login_register.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepository.findByEmail(email);
			m.addAttribute("user", user);
		}
	}

	@GetMapping("/profile")
	public String profile(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("usersList", users);
		return "admin_profile";
	}

	@GetMapping("/editUser/{id}")
	public String editEmp(@PathVariable Long id, Model m) {
		User user = userService.findUserById(id);
		m.addAttribute("user", user);
		return "edit";

	}

	@PostMapping("/updateUserDetails")
	public String updateUserDetails(@ModelAttribute User user, HttpSession session) {
		User updatedUser = userService.saveUser(user);

		if (updatedUser != null) {
			session.setAttribute("msg", "user updated successfully");
		} else {
			session.setAttribute("msg", "something went wrong");
		}
		return "redirect:/admin/profile";

	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable Long id, HttpSession session) {
		boolean isDelete = userService.deleteUserById(id);

		if (isDelete) {
			session.setAttribute("msg", "Delete sucessfully");
		} else {
			session.setAttribute("msg", "something wrong on server");
		}
		return "redirect:/admin/profile";

	}

}

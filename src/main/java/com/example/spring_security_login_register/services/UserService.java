package com.example.spring_security_login_register.services;

import com.example.spring_security_login_register.entities.User;

public interface UserService {
	public User saveUser(User user);

	public void removeSessionMsg();

	public User findUserById(Long id);

	public boolean deleteUserById(Long id);

}

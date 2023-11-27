package com.shopme.admin.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.services.UserService;

@RestController
public class UserRestController {
	
	private final UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/users/check_email")
	public String checkDuplicateEmail(@Param("id") Long id,
			@Param("email") String email) {
		return userService.isEmailUnique(id, email) ? "Ok" : "Duplicated";
	}
}

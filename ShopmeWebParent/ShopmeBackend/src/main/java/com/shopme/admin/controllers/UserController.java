package com.shopme.admin.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.admin.services.UserService;
import com.shopme.common.entity.ShopmeUser;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/users")
	public String listAll(Model model) {
		List<ShopmeUser> users = userService.listAll();
		model.addAttribute("listUsers", users);
		return "users";
	}
	
}

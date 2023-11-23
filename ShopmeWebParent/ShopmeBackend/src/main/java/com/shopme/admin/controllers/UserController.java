package com.shopme.admin.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.services.UserService;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.ShopmeUser;

@Controller
public class UserController {
	
	private static final String MESSAGE_TEXT = "message";
	
	private static final String MESSAGE_SUCCESS_SAVE_USER = "The user has been saved successfully";

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
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = userService.listRoles();
		ShopmeUser user = new ShopmeUser();
		user.setEnabled(true);
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(ShopmeUser user, RedirectAttributes redirectAttributes) {
		userService.save(user);
		
		redirectAttributes.addFlashAttribute(MESSAGE_TEXT, MESSAGE_SUCCESS_SAVE_USER);
		return "redirect:/users";
	}
	
}

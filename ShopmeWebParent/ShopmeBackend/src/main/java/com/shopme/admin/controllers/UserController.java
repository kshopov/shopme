package com.shopme.admin.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.exceptions.UserNotFoundException;
import com.shopme.admin.services.UserService;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.ShopmeUser;

@Controller
public class UserController {
	
	private static final String MESSAGE_TEXT = "message";
	
	private static final String MESSAGE_SUCCESS_SAVE_USER = "The user has been saved successfully";
	
	private static final String PAGE_TITLE_PARAM_TEXT = "pageTitle";
	
	private static final String PAGE_TITLE_PARAM_VALUE_NEW_USER = "Create New User";
	
	private static final String PAGE_TITLE_PARAM_VALUE_EDIT_USER = "Edit User with Id: ";

	private static final String PAGE_TITLE_PARAM_VALUE_DELETE_USER = "Deleted User with Id: ";
	
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
		model.addAttribute(PAGE_TITLE_PARAM_TEXT, PAGE_TITLE_PARAM_VALUE_NEW_USER);
		
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(ShopmeUser user, RedirectAttributes redirectAttributes) {
		userService.save(user);
		
		redirectAttributes.addFlashAttribute(MESSAGE_TEXT, MESSAGE_SUCCESS_SAVE_USER);
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Long id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			ShopmeUser user = userService.get(id);
			List<Role> listRoles = userService.listRoles();
			
			model.addAttribute("user", user);
			model.addAttribute("listRoles", listRoles);
			model.addAttribute(PAGE_TITLE_PARAM_TEXT, PAGE_TITLE_PARAM_VALUE_EDIT_USER + id);			

			return "user_form";
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute(MESSAGE_TEXT, ex.getMessage());
			return "redirect:/users";
		}
	}
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Long id,
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			ShopmeUser user = userService.get(id);
			userService.delete(user.getId());

			redirectAttributes.addFlashAttribute(MESSAGE_TEXT, PAGE_TITLE_PARAM_VALUE_DELETE_USER + id);
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute(MESSAGE_TEXT, ex.getMessage());
		}

		return "redirect:/users";
	}
	
}

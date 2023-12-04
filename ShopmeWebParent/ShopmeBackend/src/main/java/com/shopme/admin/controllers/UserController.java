package com.shopme.admin.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.exceptions.UserNotFoundException;
import com.shopme.admin.services.UserService;
import com.shopme.admin.util.FileUploadUtil;
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
	public String saveUser(ShopmeUser user, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			ShopmeUser savedUser = userService.save(user);
			
			String uploadDir = ShopmeUser.USER_PHOTOS_DIR + "/" + savedUser.getId();
			
			FileUploadUtil.cleanDirectory(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if(user.getPhotos().isEmpty())
				user.setPhotos(null);
			userService.save(user);
		}
		

		
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
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Long id,
			@PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		userService.updateUserEnableStatus(id, enabled);
		
		System.out.println(id + " ---------- " + enabled);
		
		String status = enabled ? "enabled" : "disabled";
		String message = String.format("The user with id %d has been %s", id, status);
		redirectAttributes.addFlashAttribute(MESSAGE_TEXT, message);
		
		return "redirect:/users";
	}
	
}

package com.shopme.admin.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.admin.exceptions.UserNotFoundException;
import com.shopme.admin.repository.user.RoleRepository;
import com.shopme.admin.repository.user.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.ShopmeUser;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;
	
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, 
			RoleRepository roleRepository, 
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public List<ShopmeUser> listAll() {
		return (List<ShopmeUser>) userRepository.findAll();
	}
	
	public List<Role> listRoles() {
		return (List<Role>) roleRepository.findAll();
	}
	
	public ShopmeUser save(ShopmeUser user) {
		if(user.getId() != null) {
			ShopmeUser existingUser = userRepository.findById(user.getId()).get();
			if(user.getPassword().trim().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			}
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		return userRepository.save(user);
	}
	
	public boolean isEmailUnique(Long id, String email) {
		ShopmeUser user = userRepository.getUserByEmail(email);
		
		if(user == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if(isCreatingNew) {
			if(user != null) return false;
		} else {
			if(user.getId() != id) {
				return false;
			}
		}
		
		return true;
	}

	public ShopmeUser get(Long id) throws UserNotFoundException {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Could not find user with id " + id));
	}

	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
	public void updateUserEnableStatus(Long id, boolean status) {
		userRepository.updateEnableStatus(id, status);
	}
}

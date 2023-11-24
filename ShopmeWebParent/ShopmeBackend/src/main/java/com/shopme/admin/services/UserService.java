package com.shopme.admin.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.admin.repository.user.RoleRepository;
import com.shopme.admin.repository.user.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.ShopmeUser;

@Service
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
	
	public void save(ShopmeUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
}

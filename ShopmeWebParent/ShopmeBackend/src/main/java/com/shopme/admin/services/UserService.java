package com.shopme.admin.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopme.admin.repository.user.UserRepository;
import com.shopme.common.entity.ShopmeUser;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<ShopmeUser> listAll() {
		return (List<ShopmeUser>) userRepository.findAll();
	}

}

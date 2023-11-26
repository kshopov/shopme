package com.shopme.admin.repository.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.ShopmeUser;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	public void testCreateUser() {
		Role admin = testEntityManager.find(Role.class, 1L);
		ShopmeUser user = new ShopmeUser("test@test.com", "123456", "Krasimir", "Shopov");
		user.addRole(admin);
		
		ShopmeUser savedUser = userRepository.save(user);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRoles() {
		Role roleAdmin = testEntityManager.find(Role.class, 1L);
		Role roleEditor = testEntityManager.find(Role.class, 3L);
		
		ShopmeUser user = new ShopmeUser("test1@test.com", "123456", "Krasimir", "Shopov");
		user.setRoles(new HashSet<>(List.of(roleAdmin, roleEditor)));
		
		ShopmeUser savedUser = userRepository.save(user);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<ShopmeUser> users = userRepository.findAll();
		users.forEach(System.out::println);
	}
	
	@Test
	public void testGetUserById() {
		ShopmeUser user = userRepository.findById(2L).get();
		assertThat(user.getId()).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		ShopmeUser user = userRepository.findById(2L).get();
		user.setEmail("test123@test.com");
		user.setEnabled(true);
		
		ShopmeUser updatedUser = userRepository.save(user);
		
		assertThat(updatedUser).isNotNull();
	}
	
	@Test
	public void testUpdateUserRoles() {
		Role admin = testEntityManager.find(Role.class, 1L);
		Role salesPerson = testEntityManager.find(Role.class, 2L);
		
		ShopmeUser user = userRepository.findById(2L).get();
		user.getRoles().remove(admin);
		user.addRole(salesPerson);
		
		ShopmeUser updatedUser = userRepository.save(user);
		assertThat(updatedUser).isNotNull();
	}
	
	@Test
	public void testDeleteUser() {
		Long userId = 2L;
		ShopmeUser user = userRepository.findById(userId).get();
		userRepository.delete(user);
		
	}
	
	@Test
	public void getUserByEmail() {
		ShopmeUser shopmeUser = userRepository.getUserByEmail("krasimir.shopov@gmail.com");
		
		assertThat(shopmeUser).isNotNull();
	}
}

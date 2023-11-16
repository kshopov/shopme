package com.shopme.admin.repository.user;

import static com.shopme.common.entity.Role.ROLE_ADMIN;
import static com.shopme.common.entity.Role.ROLE_ASSISTANT;
import static com.shopme.common.entity.Role.ROLE_EDITOR;
import static com.shopme.common.entity.Role.ROLE_SALES_PERSON;
import static com.shopme.common.entity.Role.ROLE_SHIPPER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	RoleRepository roleRepository;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role(ROLE_ADMIN, "Manage everything");
		Role savedRole = roleRepository.save(roleAdmin);
		
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles() {
		Role roleSalesPerson = new Role(ROLE_SALES_PERSON, "Manage product price, "
				+ "customers, shipping, orders and sales report");
		
		Role roleEditor = new Role(ROLE_EDITOR, "Manage categories, brands, "
				+ "products, articles and menus");
		
		Role roleShipper = new Role(ROLE_SHIPPER, "View products, orders "
				+ "and update orders status");
		
		Role roleAssistant = new Role(ROLE_ASSISTANT, "Manage questions and reviews");
		
		roleRepository.saveAll(List.of(roleSalesPerson, roleEditor, roleShipper, roleAssistant));
	}
	
}

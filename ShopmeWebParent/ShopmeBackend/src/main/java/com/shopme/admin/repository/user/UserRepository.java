package com.shopme.admin.repository.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.ShopmeUser;

public interface UserRepository extends CrudRepository<ShopmeUser, Long> {
	
	@Query("SELECT u FROM users u WHERE u.email = :email")
	public ShopmeUser getUserByEmail(@Param("email") String email);
	
}

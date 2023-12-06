package com.shopme.admin.repository.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.ShopmeUser;

public interface UserRepository extends PagingAndSortingRepository<ShopmeUser, Long>  {
	
	@Query("SELECT u FROM users u WHERE u.email = :email")
	public ShopmeUser getUserByEmail(@Param("email") String email);
	
	@Query("UPDATE users u SET u.isEnabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnableStatus(Long id, boolean enabled);
	
}

package com.shopme.admin.repository.user;

import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.ShopmeUser;

public interface UserRepository extends CrudRepository<ShopmeUser, Long> {

}

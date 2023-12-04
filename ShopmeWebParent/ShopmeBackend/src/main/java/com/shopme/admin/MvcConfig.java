package com.shopme.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.shopme.common.entity.ShopmeUser;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path userPhotosDir = Paths.get(ShopmeUser.USER_PHOTOS_DIR + "/");
		
		String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/" + ShopmeUser.USER_PHOTOS_DIR + "/**")
			.addResourceLocations("file:/" + userPhotosPath + "/");
		
	}

}

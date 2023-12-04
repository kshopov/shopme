package com.shopme.admin.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	public static void saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = file.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new IOException("Could not save file: " + fileName, e);
		}
	}
	
	public static void cleanDirectory(String dir) {
		Path path = Paths.get(dir);
		
		try {
			Files.list(path).forEach(file -> {
				if(!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					} catch (Exception e) {
						System.out.println("Could not delete file: " + file);
					}
				}
			});
		} catch (Exception e) {
			System.out.println("Could not list directory: " + path);
		}
	}
}

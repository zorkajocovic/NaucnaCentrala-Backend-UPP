package com.example.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

	 private final String path = System.getProperty("user.dir") + "\\src\\main\\resources\\files";
	    private final Path rootLocation = Paths.get(this.path);

	    public String store(MultipartFile file, String fileName) {
	        try {
	            Files.deleteIfExists(this.rootLocation.resolve(fileName + ".pdf"));
	            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName + ".pdf"));
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("FAIL!");
	        }
	        return path + "\\" +  fileName + ".pdf";
	    }
}

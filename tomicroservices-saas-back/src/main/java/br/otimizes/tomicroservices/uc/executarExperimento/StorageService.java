package br.otimizes.tomicroservices.uc.executarExperimento;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class StorageService {
	private String baseDir =  System.getProperty("user.home") + "/tomsc";
	private String uploadedDir = baseDir + "/tomicroservices-saas-uploaded";
	private String generatedDir = baseDir + "/tomicroservices-saas-generated";
	
	
	public File getUploadedFile(String fileId) {
		return new File(uploadedDir + "/" + fileId);
	}
	public String save(MultipartFile file) {
		final String id = UUID.randomUUID().toString();
		try (InputStream input = file.getInputStream()) {
			Files.copy(
					input, 
					Path.of(uploadedDir  + "/" + id), 
					StandardCopyOption.REPLACE_EXISTING);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}

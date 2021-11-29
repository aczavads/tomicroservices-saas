package br.otimizes.tomicroservices.uc.executarExperimento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/experimentos")
public class GerenciadorExecutarExperimento {	
	@Autowired
	private ServiceExecutarExperimento service;
	
	
	@PostMapping("/upload/features-file")
	public String handleUploadFeatureFile(@RequestParam("file") MultipartFile file) {
		return service.save(file);
	}
	
	@PostMapping("/upload/dynamic-log-file")
	public String handleUploadLogFile(@RequestParam("file") MultipartFile file) {
		return service.save(file);
	}

	@PostMapping
	public List<List<String>> executar(@RequestBody ExperimentRequest request) {
		return service.executar(request);
	}


}

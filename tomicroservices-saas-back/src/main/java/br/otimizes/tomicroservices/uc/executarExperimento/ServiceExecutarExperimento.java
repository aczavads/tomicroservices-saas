package br.otimizes.tomicroservices.uc.executarExperimento;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.uma.jmetal.util.pseudorandom.PseudoRandomGenerator;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

import br.pucrio.inf.les.opus.tomicroservices.analysis.ast.ClassNamePattern;
import br.pucrio.inf.les.opus.tomicroservices.analysis.ast.ReadDependencyFinderFile;
import br.pucrio.inf.les.opus.tomicroservices.analysis.dynamic.DynamicLogAnalyzer;
import br.pucrio.inf.les.opus.tomicroservices.graph.Graph;
import br.pucrio.inf.les.opus.tomicroservices.metrics.MetricPerMicroserviceArchitecture;
import br.pucrio.inf.les.opus.tomicroservices.optimization.algorithm.nsgaIII.toMicroservices.NSGAIIIRunner;

@Service
public class ServiceExecutarExperimento {
	private StorageService storageService = new StorageService();

	public String save(MultipartFile file) {
		return storageService.save(file);
	}

	public List<List<String>> executar(ExperimentRequest request) {
		System.out.println(request);
		
		File acceptListFile = storageService.getUploadedFile(request.getAcceptListFileId());
		File rejectListFile = storageService.getUploadedFile(request.getRejectListFileId());
		File staticLogFile = storageService.getUploadedFile(request.getStaticLogFileId());
		File dynamicLogFile = storageService.getUploadedFile(request.getDynamicLogFileId());
		File featuresFile = storageService.getUploadedFile(request.getFeaturesFileId());
		
		ClassNamePattern pattern = new ClassNamePattern(acceptListFile, true);
		ClassNamePattern reject = new ClassNamePattern(rejectListFile, false);
		
		Graph graph = new Graph();
		ReadDependencyFinderFile dependencyFinder = new ReadDependencyFinderFile();
		dependencyFinder.insertInGraphFromFile(staticLogFile, graph, pattern, reject);
		
		DynamicLogAnalyzer dynamic = new DynamicLogAnalyzer();
		dynamic.analyze(dynamicLogFile, graph, featuresFile);

		List<MetricPerMicroserviceArchitecture> metrics = new ArrayList<MetricPerMicroserviceArchitecture>();
		for (MetricDTO metricDTO:  request.getMetrics()) {
			metrics.add(MetricFactory.newInstance(metricDTO));
		}
		
		PseudoRandomGenerator random = new JavaRandomGenerator();
		NSGAIIIRunner runner = new NSGAIIIRunner();

		List<List<String>> resultado = new ArrayList<>();
		for (int i = 0; i < request.getExecutions(); ++i) {
			File file = new File(System.getProperty("user.home") + "/tomsc/results/result" + i);
			runner._execute(graph, metrics, request.getNumberOfMicroservices(), request.getCrossoverProbability(), request.getCrossoverFraction(), random, file);			
			generateExecutionResult(resultado, file);			
		}
		return resultado;
	} 

	private void generateExecutionResult(List<List<String>> resultado, File file) {
		try {
			List<String> lines = new ArrayList<>();
			Scanner s = new Scanner(file);
			StringBuffer sb = new StringBuffer();						
			while (s.hasNextLine()) {
				lines.add(s.nextLine());
			}
			resultado.add(lines);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}

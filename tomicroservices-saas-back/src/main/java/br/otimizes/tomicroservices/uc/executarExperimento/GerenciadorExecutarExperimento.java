package br.otimizes.tomicroservices.uc.executarExperimento;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.jmetal.util.pseudorandom.PseudoRandomGenerator;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

import br.pucrio.inf.les.opus.tomicroservices.analysis.ast.ClassNamePattern;
import br.pucrio.inf.les.opus.tomicroservices.analysis.ast.ReadDependencyFinderFile;
import br.pucrio.inf.les.opus.tomicroservices.analysis.dynamic.DynamicLogAnalyzer;
import br.pucrio.inf.les.opus.tomicroservices.graph.Graph;
import br.pucrio.inf.les.opus.tomicroservices.metrics.ConvertValue;
import br.pucrio.inf.les.opus.tomicroservices.metrics.MetricPerMicroserviceArchitecture;
import br.pucrio.inf.les.opus.tomicroservices.metrics.Minimize;
import br.pucrio.inf.les.opus.tomicroservices.metrics.overhead.CohesionPerMicroserviceArchitecture;
import br.pucrio.inf.les.opus.tomicroservices.metrics.overhead.CouplingPerMicroserviceArchitecture;
import br.pucrio.inf.les.opus.tomicroservices.metrics.overhead.FunctionalityPerMicroserviceArchitecture;
import br.pucrio.inf.les.opus.tomicroservices.optimization.algorithm.nsgaIII.toMicroservices.NSGAIIIRunner;

@RestController
@RequestMapping("/api/experimentos")
public class GerenciadorExecutarExperimento {

	@PostMapping
	public String executar(@RequestBody ExperimentRequest request) {
		System.out.println(request);
		
		String acceptList = "/home/arthur/Documents/doutorado/tomsc/accept.list";
		String rejectList = "/home/arthur/Documents/doutorado/tomsc/reject.list";
		String dependency = "/home/arthur/Documents/doutorado/tomsc/csbaseDependency";
		String logDynamic = "/home/arthur/Documents/doutorado/tomsc/log";
		String featuresGeneral = "/home/arthur/Documents/doutorado/tomsc/feature";

		File accepListFile = new File(acceptList);
		File rejectListFile = new File(rejectList);
		File staticFile = new File(dependency);
		File logDynamicFile = new File(logDynamic);
		File featuresGeneralFile = new File(featuresGeneral);
		ClassNamePattern pattern = new ClassNamePattern(accepListFile, true);
		ClassNamePattern reject = new ClassNamePattern(rejectListFile, false);
		Graph graph = new Graph();
		ReadDependencyFinderFile dependencyFinder = new ReadDependencyFinderFile();
		dependencyFinder.insertInGraphFromFile(staticFile, graph, pattern, reject);
		DynamicLogAnalyzer dynamic = new DynamicLogAnalyzer();
		dynamic.analyze(logDynamicFile, graph, featuresGeneralFile);

		List<MetricPerMicroserviceArchitecture> metrics;
		metrics = new ArrayList<MetricPerMicroserviceArchitecture>();
		ConvertValue minimize = new Minimize();

		// metrics.add(new OverheadMaxPerMicroserviceArchitecture());
		metrics.add(new FunctionalityPerMicroserviceArchitecture(minimize));
		// metrics.add(new ReusePerMicroserviceArchitecture("start", 1, minimize));

		metrics.add(new CouplingPerMicroserviceArchitecture());
		metrics.add(new CohesionPerMicroserviceArchitecture(minimize));
		// metrics.add(new SizePerMicroserviceArchitecture());
		PseudoRandomGenerator random = new JavaRandomGenerator();

		NSGAIIIRunner runner = new NSGAIIIRunner();

		// br.pucrio.inf.les.opus.tomicroservices.optimization.NSGAIIRunner runner
		// = new br.pucrio.inf.les.opus.tomicroservices.optimization.NSGAIIRunner();
		List<MetricPerMicroserviceArchitecture> otherMetrics = new ArrayList<MetricPerMicroserviceArchitecture>();
		// otherMetrics.add(new OverheadMaxPerMicroserviceArchitecture());
		// otherMetrics.add(new FunctionalityPerMicroserviceArchitecture(minimize));
		// otherMetrics.add(new ReusePerMicroserviceArchitecture("start", 1, minimize));

		metrics.addAll(otherMetrics);
		final int executions = 7;
		for (int i = 0; i < executions; ++i) {
			File file = new File("/home/arthur/Documents/doutorado/tomsc/result" + i);

			runner._execute(graph, metrics, request.getNumberOfMicroservices(), random, file);

			// runner.execute(graph, metrics,
			// numberOfMicroservices,
			// random, file, otherMetrics);
		}
		return "Opa! :D";
	}

}

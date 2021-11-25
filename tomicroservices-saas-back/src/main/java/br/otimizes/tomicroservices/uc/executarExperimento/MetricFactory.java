package br.otimizes.tomicroservices.uc.executarExperimento;

import br.pucrio.inf.les.opus.tomicroservices.metrics.ConvertValue;
import br.pucrio.inf.les.opus.tomicroservices.metrics.MetricPerMicroserviceArchitecture;
import br.pucrio.inf.les.opus.tomicroservices.metrics.Minimize;
import br.pucrio.inf.les.opus.tomicroservices.metrics.overhead.CohesionPerMicroserviceArchitecture;
import br.pucrio.inf.les.opus.tomicroservices.metrics.overhead.CouplingPerMicroserviceArchitecture;
import br.pucrio.inf.les.opus.tomicroservices.metrics.overhead.FunctionalityPerMicroserviceArchitecture;
import br.pucrio.inf.les.opus.tomicroservices.metrics.overhead.OverheadMaxPerMicroserviceArchitecture;
import br.pucrio.inf.les.opus.tomicroservices.metrics.overhead.ReusePerMicroserviceArchitecture;
import br.pucrio.inf.les.opus.tomicroservices.metrics.overhead.SizePerMicroserviceArchitecture;

public class MetricFactory {

	public static MetricPerMicroserviceArchitecture newInstance(MetricDTO metricDTO) {
		if (metricDTO.getName().equalsIgnoreCase("Functionality")) {
			ConvertValue minimize = new Minimize();
			return new FunctionalityPerMicroserviceArchitecture(minimize);
		}
		if (metricDTO.getName().equalsIgnoreCase("OverheadMax")) {
			return new OverheadMaxPerMicroserviceArchitecture();
		}
		if (metricDTO.getName().equalsIgnoreCase("Reuse")) {
			ConvertValue minimize = new Minimize();
			return new ReusePerMicroserviceArchitecture("start", 1, minimize);
		}
		if (metricDTO.getName().equalsIgnoreCase("Coupling")) {
			return new CouplingPerMicroserviceArchitecture();
		}
		if (metricDTO.getName().equalsIgnoreCase("Cohesion")) {
			ConvertValue minimize = new Minimize();
			return new CohesionPerMicroserviceArchitecture(minimize);			
		}
		if (metricDTO.getName().equalsIgnoreCase("Size")) {
			return new SizePerMicroserviceArchitecture();			
		}
		return new NullMetricPerMicroserviceArchitecture();
	}
}
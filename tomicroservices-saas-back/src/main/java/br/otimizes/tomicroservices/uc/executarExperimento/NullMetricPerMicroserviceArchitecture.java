package br.otimizes.tomicroservices.uc.executarExperimento;

import java.util.ArrayList;
import java.util.List;

import br.pucrio.inf.les.opus.tomicroservices.metrics.MetricPerMicroservice;
import br.pucrio.inf.les.opus.tomicroservices.metrics.MetricPerMicroserviceArchitecture;
import br.pucrio.inf.les.opus.tomicroservices.optimization.search.MicroservicesSolution;

public class NullMetricPerMicroserviceArchitecture implements MetricPerMicroserviceArchitecture {
	private int objectiveIndex;

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public void setObjectiveIndex(int index) {
		this.objectiveIndex = index;
	}

	@Override
	public double printableValue(double value) {
		return 0;
	}

	@Override
	public double printableValue(MicroservicesSolution microservicesSolution) {
		return 0;
	}

	@Override
	public double getValue(MicroservicesSolution microservicesSolution) {
		return 0;
	}

	@Override
	public int getObjectiveIndex() {
		return this.objectiveIndex;
	}

	@Override
	public List<MetricPerMicroservice> getMetricPerMicroservice() {
		return new ArrayList<>();
	}
}
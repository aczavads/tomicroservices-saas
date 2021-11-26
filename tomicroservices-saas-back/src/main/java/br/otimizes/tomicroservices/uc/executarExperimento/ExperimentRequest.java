package br.otimizes.tomicroservices.uc.executarExperimento;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
public class ExperimentRequest {
	private int numberOfMicroservices;
	private int executions;
	private String[] metricNames;
	private MetricDTO[] metrics = new MetricDTO[]{};	
	private double crossoverProbability; 
	private double crossoverFraction;
	private String logFileId;
	private String featureFileId;
	private String acceptListFileId;
	private String rejectListFileId;
    private String staticLogFileId;
    private String dynamicLogFileId;
    private String featuresFileId;
	
		
}
 
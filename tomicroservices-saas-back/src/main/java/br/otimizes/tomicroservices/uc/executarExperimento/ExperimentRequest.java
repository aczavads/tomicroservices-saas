package br.otimizes.tomicroservices.uc.executarExperimento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class ExperimentRequest {
	private int numberOfMicroservices;

}

import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from "yup";
import api from '../../services/api';

const schema = yup.object({
    numberOfMicroservices: yup.number().positive().integer().required(),
    executions: yup.number().positive().integer().required(),
    crossoverProbability: yup.number().min(0.0).max(1.0).required(),
    crossoverFraction: yup.number().min(0.0).max(1.0).required(),
}).required();


const ExecutarExperimento = () => {
    const [executing, setExecuting] = useState(false);
    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(schema)
    });

    const onSubmit = data => console.log(data);

    const executarExperimento = async (data) => {
        //alert("Opa, executando!!!");
        if (executing) {
            alert("Opa, espera aí!");
        }
        setExecuting(true);
        try {
            var request = {
                numberOfMicroservices: data.numberOfMicroservices,
                executions: data.executions,
                crossoverProbability: data.crossoverProbability,
                crossoverFraction: data.crossoverFraction,
                dynamicLogFileId: "803177c9-cde0-469e-bd42-c5e6972bae05",
                metrics: [
                ]
            };
            request.metrics = data.couplingMetric ? [...request.metrics, "Coupling"] :  request.metrics;
            request.metrics = data.cohesionMetric ? [...request.metrics, "Cohesion"] :  request.metrics;
            request.metrics = data.overheadMax ? [...request.metrics, "OverheadMax"] :  request.metrics;
            request.metrics = data.functionality ? [...request.metrics, "Functionality"] :  request.metrics;
            request.metrics = data.size ? [...request.metrics, "Size"] :  request.metrics;
            request.metrics = data.reuse ? [...request.metrics, "Reuse"] :  request.metrics;
            
            console.log(data);
            console.log(request);

            const postRequest = await api.post("/api/experimentos", request);
            console.log(postRequest.data);
        } catch (error) {
            console.log(error);
        }
        setExecuting(false);
    }


    return (

        <form onSubmit={handleSubmit(onSubmit)}>
            <div className="form-row">
                <label htmlFor="inputNumberOfMicroservices">Número de microserviços</label>
                <div className="form-group col-5">
                    <input {...register("numberOfMicroservices")} id="inputNumberOfMicroservices" className={`form-control ${errors.numberOfMicroservices ? 'is-invalid' : ''}`} />
                    <div className="invalid-feedback">{errors.numberOfMicroservices?.message}</div>
                </div>
            </div>
            <div className="form-row">
                <label htmlFor="inputExecutions">Número de execuções</label>
                <div className="form-group col-5">
                    <input {...register("executions")} id="inputExecutions" className={`form-control ${errors.executions ? 'is-invalid' : ''}`} />
                    <div className="invalid-feedback">{errors.executions?.message}</div>
                </div>
            </div>
            <div className="form-row">
                <label htmlFor="inputCrossoverProbability">Probabilidade de crossover (de 0.0 a 1.0)</label>
                <div className="form-group col-5">
                    <input {...register("crossoverProbability")} id="inputCrossoverProbability" className={`form-control ${errors.crossoverProbability ? 'is-invalid' : ''}`} />
                    <div className="invalid-feedback">{errors.crossoverProbability?.message}</div>
                </div>
            </div>
            <div className="form-row">
                <label htmlFor="inputCrossoverFraction">Fração de crossover (de 0.0 a 1.0)</label>
                <div className="form-group col-5">
                    <input {...register("crossoverFraction")} id="inputCrossoverFraction" className={`form-control ${errors.crossoverFraction ? 'is-invalid' : ''}`} />
                    <div className="invalid-feedback">{errors.crossoverFraction?.message}</div>
                </div>
            </div>
            <div className="form-row">
                <div class="form-check">
                    <input {...register("couplingMetric")} className="form-check-input" type="checkbox" value="" id="checkboxCoupling"></input>
                    <label className ="form-check-label" for="checkboxCoupling">
                    Coupling
                    </label>
                </div>
            </div>
            <div className="form-row">
                <div class="form-check">
                    <input {...register("cohesionMetric")} className="form-check-input" type="checkbox" value="" id="checkboxCohesion"></input>
                    <label className ="form-check-label" for="checkboxCohesion">
                    Cohesion
                    </label>
                </div>
            </div>
            <div className="form-row">
                <div class="form-check">
                    <input {...register("overheadMax")} className="form-check-input" type="checkbox" value="" id="checkboxOverheadMax"></input>
                    <label className ="form-check-label" for="checkboxOverheadMax">
                    OverheadMax
                    </label>
                </div>
            </div>
            <div className="form-row">
                <div class="form-check">
                    <input {...register("functionality")} className="form-check-input" type="checkbox" value="" id="checkboxFunctionality"></input>
                    <label className ="form-check-label" for="checkboxFunctionality">
                    Functionality
                    </label>
                </div>
            </div>
            <div className="form-row">
                <div class="form-check">
                    <input {...register("size")} className="form-check-input" type="checkbox" value="" id="checkboxSize"></input>
                    <label className ="form-check-label" for="checkboxSize">
                    Size
                    </label>
                </div>
            </div>
            <div className="form-row">
                <div class="form-check">
                    <input {...register("reuse")} className="form-check-input" type="checkbox" value="" id="checkboxReuse"></input>
                    <label className ="form-check-label" for="checkboxReuse">
                    Reuse
                    </label>
                </div>
            </div>
            <br />
            <div className="form-row">
                <button className="btn btn-primary" type="submit" onClick={handleSubmit(executarExperimento)}>Executar Experimento</button>
            </div>
        </form>
    );
}


export default ExecutarExperimento;
import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from "yup";
import api from '../../services/api';

const schema = yup.object({
    numberOfMicroservices: yup.number().positive().integer().required(),
    executions: yup.number().positive().integer().required()
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
            const postRequest = await api.post("/api/experimentos", { 
                numberOfMicroservices: data.numberOfMicroservices,
                executions : data.executions,
                crossoverProbability: 0.8,
                crossoverFraction: 0.8,            
                dynamicLogFileId: "803177c9-cde0-469e-bd42-c5e6972bae05",
                metrics : [	
                    "Coupling", 
                    "Cohesion",
                    "##Reuse"
                ]	            
            });
            console.log(postRequest.data);
        } catch (error) {
            console.log(error);
        }
        setExecuting(false);
    }


    return (

        <form onSubmit={handleSubmit(onSubmit)}>
            <div class="form-row">
                <label for="inputNumberOfMicroservices">Número de microserviços</label>
                <div className="form-group col-5">
                    <input {...register("numberOfMicroservices")} id="inputNumberOfMicroservices" className={`form-control ${errors.numberOfMicroservices ? 'is-invalid' : ''}`} />
                    <div className="invalid-feedback">{errors.numberOfMicroservices?.message}</div>
                </div>
            </div>
            <div class="form-row">
                <label for="inputExecutions">Número de execuções</label>
                <div className="form-group col-5">
                    <input {...register("executions")} id="inputExecutions" className={`form-control ${errors.executions ? 'is-invalid' : ''}`} />
                    <div className="invalid-feedback">{errors.executions?.message}</div>
                </div>
            </div>
            <br />
            <div class="form-row">
                <button class="btn btn-primary" type="submit" onClick={handleSubmit(executarExperimento)}>Executar Experimento</button>
            </div>
        </form>
    );
}


export default ExecutarExperimento;
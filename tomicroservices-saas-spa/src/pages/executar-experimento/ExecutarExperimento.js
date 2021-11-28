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
    const [fileChanged, setFileChanged] = useState(false);
    const [newFile, setNewFile] = useState(null);
    const [dynamicLogFileId, setDynamicLogFileId] = useState(null);
    const [results, setResults] = useState([]);
    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(schema)
    });

    const onFileChange = event => {
        setFileChanged(true);
        setNewFile(event.target.files[0]);
    };

    const onFileUpload = event => {
        uploadFile(event);
    };

    const onSubmit = data => console.log(data);

    const uploadFile = async (event) => {
        if (executing) {
            alert("Já existe uma execução em andamento!");
        }
        setExecuting(true);
        try {
            const formData = new FormData();
            formData.append(
                "file",
                newFile,
                newFile.name
            );
            console.log(newFile);
            const postRequest = await api.post("/api/experimentos/upload/dynamic-log-file", formData);
            console.log(postRequest.data);
            setDynamicLogFileId(postRequest.data);
        } catch (error) {
            console.log(error);
        }
        setFileChanged(false);
        setExecuting(false);
    }
    const executarExperimento = async (data) => {
        //alert("Opa, executando!!!");
        if (executing) {
            alert("Já existe uma execução em andamento!");
            return;
        }
        if (dynamicLogFileId === undefined || dynamicLogFileId === null) {
            alert("É necessário fazer o upload de um arquivo de log dinâmico para iniciar uma execução!")
            return;
        }
        try {
            var request = {
                numberOfMicroservices: data.numberOfMicroservices,
                executions: data.executions,
                crossoverProbability: data.crossoverProbability,
                crossoverFraction: data.crossoverFraction,
                dynamicLogFileId: dynamicLogFileId,
                //dynamicLogFileId: "803177c9-cde0-469e-bd42-c5e6972bae05",
                metrics: [
                ]
            };
            request.metrics = data.couplingMetric ? [...request.metrics, "Coupling"] : request.metrics;
            request.metrics = data.cohesionMetric ? [...request.metrics, "Cohesion"] : request.metrics;
            request.metrics = data.overheadMax ? [...request.metrics, "OverheadMax"] : request.metrics;
            request.metrics = data.functionality ? [...request.metrics, "Functionality"] : request.metrics;
            request.metrics = data.size ? [...request.metrics, "Size"] : request.metrics;
            request.metrics = data.reuse ? [...request.metrics, "Reuse"] : request.metrics;

            if (request.metrics.length === 0) {
                alert("É necessário selecionar pelo menos uma métrica para iniciar uma execução!")
                return;

            }
            setExecuting(true);
            console.log(data);
            console.log(request);

            const postRequest = await api.post("/api/experimentos", request);
            console.log(postRequest);
            setResults(postRequest.data);
        } catch (error) {
            console.log(error);
        }
        setExecuting(false);
    }


    return (

        <form onSubmit={handleSubmit(onSubmit)}>
            <div className="form-row">
                <label htmlFor="formFileSm" className="form-label">Arquivo de log dinâmico</label>
                <div className="form-group col-5">
                    <input className="form-control form-control-sm" id="formFileSm" type="file" onChange={onFileChange} />
                    <button className={`btn btn-primary ${fileChanged ? '' : 'disabled'}`} type="button" onClick={onFileUpload}>Carregar arquivo</button>
                </div>
            </div>
            <br />
            <div className="form-row">
                <label htmlFor="inputNumberOfMicroservices">Número de microserviços</label>
                <div className="form-group col-5">
                    <input {...register("numberOfMicroservices")} id="inputNumberOfMicroservices" className={`form-control form-control-sm ${errors.numberOfMicroservices ? 'is-invalid' : ''}`} />
                    <div className="invalid-feedback">{errors.numberOfMicroservices?.message}</div>
                </div>
            </div>
            <div className="form-row">
                <label htmlFor="inputExecutions">Número de execuções</label>
                <div className="form-group col-5">
                    <input {...register("executions")} id="inputExecutions" className={`form-control form-control-sm ${errors.executions ? 'is-invalid' : ''}`} />
                    <div className="invalid-feedback">{errors.executions?.message}</div>
                </div>
            </div>
            <div className="form-row">
                <label htmlFor="inputCrossoverProbability">Probabilidade de crossover (de 0.0 a 1.0)</label>
                <div className="form-group col-5">
                    <input {...register("crossoverProbability")} id="inputCrossoverProbability" className={`form-control form-control-sm ${errors.crossoverProbability ? 'is-invalid' : ''}`} />
                    <div className="invalid-feedback">{errors.crossoverProbability?.message}</div>
                </div>
            </div>
            <div className="form-row">
                <label htmlFor="inputCrossoverFraction">Fração de crossover (de 0.0 a 1.0)</label>
                <div className="form-group col-5">
                    <input {...register("crossoverFraction")} id="inputCrossoverFraction" className={`form-control form-control-sm ${errors.crossoverFraction ? 'is-invalid' : ''}`} />
                    <div className="invalid-feedback">{errors.crossoverFraction?.message}</div>
                </div>
            </div>
            <div className="form-row">
                <div className="form-check">
                    <input {...register("couplingMetric")} className="form-check-input" type="checkbox" value="" id="checkboxCoupling"></input>
                    <label className="form-check-label" htmlFor="checkboxCoupling">
                        Coupling
                    </label>
                </div>
            </div>
            <div className="form-row">
                <div className="form-check">
                    <input {...register("cohesionMetric")} className="form-check-input" type="checkbox" value="" id="checkboxCohesion"></input>
                    <label className="form-check-label" htmlFor="checkboxCohesion">
                        Cohesion
                    </label>
                </div>
            </div>
            <div className="form-row">
                <div className="form-check">
                    <input {...register("overheadMax")} className="form-check-input" type="checkbox" value="" id="checkboxOverheadMax"></input>
                    <label className="form-check-label" htmlFor="checkboxOverheadMax">
                        OverheadMax
                    </label>
                </div>
            </div>
            <div className="form-row">
                <div className="form-check">
                    <input {...register("functionality")} className="form-check-input" type="checkbox" value="" id="checkboxFunctionality"></input>
                    <label className="form-check-label" htmlFor="checkboxFunctionality">
                        Functionality
                    </label>
                </div>
            </div>
            <div className="form-row">
                <div className="form-check">
                    <input {...register("size")} className="form-check-input" type="checkbox" value="" id="checkboxSize"></input>
                    <label className="form-check-label" htmlFor="checkboxSize">
                        Size
                    </label>
                </div>
            </div>
            <div className="form-row">
                <div className="form-check">
                    <input {...register("reuse")} className="form-check-input" type="checkbox" value="" id="checkboxReuse"></input>
                    <label className="form-check-label" htmlFor="checkboxReuse">
                        Reuse
                    </label>
                </div>
            </div>
            <br />
            <div className="form-row">
                <button className="btn btn-primary" type="submit" onClick={handleSubmit(executarExperimento)}>Executar Experimento</button>
            </div>
            <br />
            <hr />
            <AccordionResults results={results}></AccordionResults>
        </form>
    );
}

const RenderResults = (props) => {
    const resultsRendered = props.results.map((result, index) => {
        return (
            <div className="accordion-item" key={index}>
                <h2 className="accordion-header" id={`heading${index}`}>
                    <button className="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target={`#collapse${index}`} aria-expanded="true" aria-controls={`collapse${index}`}>
                        {`Execução ${index}`}
                    </button>
                </h2>
                <div id={`collapse${index}`} className="accordion-collapse collapse show" aria-labelledby={`heading${index}`} data-bs-parent="#accordionExample">
                    <div className="accordion-body">
                        {result.map((value, valueIndex) => <p key={valueIndex}>{value}</p>)}
                    </div>
                </div>
            </div>
        );
    });
    return (<React.Fragment>{resultsRendered}</React.Fragment>);
}

const AccordionResults = (props) => {
    if (props.results.length === 0) {
        return (<React.Fragment></React.Fragment>);
    }
    return (
        <div className="accordion" id="accordionExample">
            <RenderResults results={props.results}></RenderResults>
        </div>
    )
}


export default ExecutarExperimento;
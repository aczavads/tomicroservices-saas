import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from "yup";
import api from '../services/api';

const schema = yup.object({
    firstName: yup.string().required(),
    age: yup.number().positive().integer().required(),
}).required();


export default function Login() {
    const [executing, setExecuting] = useState(false);
    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(schema)
    });

    const onSubmit = data => console.log(data);

    const executarExperimento = async () => {
        //alert("Opa, executando!!!");
        if (executing) {
            alert("Opa, espera aí!");
        }
        setExecuting(true);
        try {
            const postRequest = await api.post("/api/experimentos", { numberOfMicroservices: 3 });
            console.log(postRequest.data);
        } catch (error) {
            console.log(error);
        }
        setExecuting(false);
    }


    return (

        <form onSubmit={handleSubmit(onSubmit)}>
            <div class="form-row">
                <label for="inputFirstName">Firstname </label>
                <div className="form-group col-5">
                    <input {...register("firstName")} id="inputFirstName" className={`form-control ${errors.firstName ? 'is-invalid' : ''}`} />
                    <div className="invalid-feedback">{errors.firstName?.message}</div>
                </div>
            </div>

            <div class="form-row">
                <div className="form-group col-5">
                    <label for="inputAge">Firstname </label>
                    <input {...register("age")} id="inputAge" className={`form-control ${errors.age ? 'is-invalid' : ''}`} />
                    <div className="invalid-feedback">{errors.age?.message}</div>
                </div>
            </div>

            <div class="form-row">
                <button class="btn btn-primary" type="submit">Submit form</button>
            </div>
            <br />
            <div class="form-row">
                <button class="btn btn-primary" type="submit" onClick={executarExperimento}>Executar Experimento</button>
            </div>
        </form>
    );
}
import React from "react";
import { useForm } from "react-hook-form";
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from "yup";

const schema = yup.object({
    firstName: yup.string().required(),
    age: yup.number().positive().integer().required(),
}).required();

export default function Login() {
    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(schema)
    });
    const onSubmit = data => console.log(data);

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
        </form>
    );
}
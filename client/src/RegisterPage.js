import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import 'react-phone-input-2/lib/style.css'
import "./style/RegisterPage.css";
import {register} from "./utils/restcalls/user";
import {useNavigate} from "react-router-dom";

function RegisterPage() {
    const navigate = useNavigate();
    const [registerForm, setRegisterForm] = useState({
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
    });

    const [error, setError] = useState("");

    const handleUpdate = (event) => {
        let name = event.target.name;
        let value = event.target.value;
        setRegisterForm({ ...registerForm, [name]: value });
    };

    const handleRegister = async (event) => {
        event.preventDefault();
        if (
            registerForm.username === "" ||
            registerForm.email === "" ||
            registerForm.password === "" ||
            registerForm.confirmPassword === ""
        ) {
            setError("Please fill all the details");
        } else if (registerForm.password !== registerForm.confirmPassword) {
            setError("Passwords do not match");
        } else {
            registerForm.confirmPassword = undefined;
            register(registerForm).then(data => {
                navigate('/login');
            }).catch(error => setError("Invalid credentials"));
        }
    }

    return (
        <div className="page-container">
            <h1 className="tile-title">Create your fys account</h1>
            <div className="form-container">
                <Form>
                    {error && <p className="text-danger">{error}</p>}

                    <Form.Group className="mb-3" controlId="username">
                        <Form.Label>Username</Form.Label>
                        <Form.Control
                            type="text"
                            name={"username"}
                            placeholder="Enter Username"
                            value={registerForm.username}
                            onChange={handleUpdate}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="email">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            type="email"
                            name={"email"}
                            placeholder="Enter Email"
                            value={registerForm.email}
                            onChange={handleUpdate}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="password">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            name={"password"}
                            placeholder="Enter Password"
                            value={registerForm.password}
                            onChange={handleUpdate}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="confirmPassword">
                        <Form.Label>Confirm Password</Form.Label>
                        <Form.Control
                            type="password"
                            name={"confirmPassword"}
                            placeholder="Confirm Password"
                            value={registerForm.confirmPassword}
                            onChange={handleUpdate}
                        />
                    </Form.Group>

                    <div className="text-center">
                        <Button
                            variant="primary"
                            type="submit"
                            className="register-button"
                            onClick={handleRegister}>
                            Register
                        </Button>
                        <p className="mt-3">
                            Already have an account?{" "}
                            <a href="/login" className="login-link">
                                Log in
                            </a>
                        </p>
                    </div>
                </Form>
            </div>
        </div>
    );
}

export default RegisterPage;



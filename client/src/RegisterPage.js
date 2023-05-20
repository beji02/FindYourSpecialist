import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import 'react-phone-input-2/lib/style.css'
import "./style/RegisterPage.css";

function RegisterPage() {
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
                            onClick={async (event) => {
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
                                    await fetch("register", {
                                        method: "POST",
                                        headers: {
                                            Accept: "application/json",
                                            "Content-Type": "application/json",
                                        },
                                        body: JSON.stringify(registerForm),
                                    }).then((response) => {
                                        if (response.status === 200) {
                                            window.location.href = "/login";
                                        } else {
                                            console.log(response);
                                            setError("Failed to register");
                                        }
                                    });
                                }
                            }}
                        >
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



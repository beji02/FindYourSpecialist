import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { useNavigate } from "react-router-dom";
import "./style/LoginPage.css";

function LoginPage() {
    const navigate = useNavigate();
    const [loginForm, setLoginForm] = useState({
        username: "",
        password: "",
    });
    const [error, setError] = useState("");

    const handleUpdate = (event) => {
        let name = event.target.name;
        let value = event.target.value;
        setLoginForm({ ...loginForm, [name]: value });
    };

    const handleLogin = async (event) => {
        event.preventDefault();
        if (loginForm.username === "" || loginForm.password === "") {
            setError("Please fill all the details");
        } else {
            try {
                const response = await fetch("login", {
                    method: "POST",
                    headers: {
                        Accept: "application/json",
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(loginForm),
                });

                if (response.status === 200) {
                    const data = await response.json();
                    const token = data.token;
                    localStorage.setItem("token", token);

                    navigate('/home');
                } else {
                    setError("Invalid credentials");
                }
            } catch (error) {
                setError("An error occurred. Please try again.");
            }
        }
    };

    return (
        <div className="page-container">
            <h1 className="tile-title">Login to fys</h1>
            <div className="form-container">
                <Form>
                    {error && <p className="text-danger">{error}</p>}

                    <Form.Group className="mb-3" controlId="username">
                        <Form.Label>Username</Form.Label>
                        <Form.Control
                            type="text"
                            name={"username"}
                            placeholder="Enter Username"
                            value={loginForm.username}
                            onChange={handleUpdate}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="password">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            name={"password"}
                            placeholder="Enter Password"
                            value={loginForm.password}
                            onChange={handleUpdate}
                        />
                    </Form.Group>
                    <div className="text-center">
                        <Button
                            variant="primary"
                            type="submit"
                            className="login-button"
                            onClick={handleLogin}
                        >
                            Log in
                        </Button>
                        <p className="mt-3">
                            New to the platform?{" "}
                            <a href="/register" className="new-account-link">
                                Create an account
                            </a>
                        </p>
                    </div>
                </Form>
            </div>
        </div>
    );
}

export default LoginPage;

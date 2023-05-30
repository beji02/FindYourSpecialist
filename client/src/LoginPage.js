import React, {useEffect, useState} from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import {useNavigate} from "react-router-dom";
import "./style/LoginPage.css";
import {login} from "./utils/restcalls/user";

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
        setLoginForm({...loginForm, [name]: value});
    };

    const handleLogin = async (event) => {
        event.preventDefault();
        if (loginForm.username === "" || loginForm.password === "") {
            setError("Please fill all the details");
        } else {
            login(loginForm).then(data => {
                    const token = data.token;
                    const roles = data.roles;

                    localStorage.setItem("token", token);
                    localStorage.setItem("roles", roles);

                    navigate('/home');
            }).catch(error => setError("Invalid credentials"));
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

import React, {useState} from "react";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import { LoginFormDto } from "./domain/dto/loginFormDto";
function Login() {
    const [loginForm, setLoginForm] = useState(new LoginFormDto());
    const [error, setError] = useState('');

    const handleUpdate = (event) => {
        let name = event.target.name
        let value = event.target.value
        setLoginForm({...loginForm, [name]:value})
    }
    return (
        <Container className="d-flex text-center justify-content-center align-items-center"
                   style={{ minHeight: "100vh" }}>

            <Form>
                {error && <p className="text-danger">{error}</p>}
                <h1 className="mb-3">Login</h1>
                <Form.Group className="mb-3" controlId="username">
                    <Form.Control type="text" name={'username'} placeholder="Enter Username" value={loginForm.username}
                        onChange={handleUpdate}
                    />
                </Form.Group>

                <Form.Group className="mb-3" controlId="password">
                    <Form.Control type="password" name={'password'} placeholder="Enter Password" value={loginForm.password}
                        onChange={handleUpdate}
                    />
                </Form.Group>
                <Button variant="primary" type="submit" onClick={
                    async (event) => {
                        event.preventDefault();
                        if (loginForm.username === "" || loginForm.password === "") {
                            setError("Please fill all the details");
                        } else {
                            await fetch('login', {
                                method: 'POST',
                                headers: {
                                    'Accept': 'application/json',
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(loginForm),
                            }).then((response) => {
                                if (response.status === 200) {
                                    window.location.href = "/";
                                } else {
                                    setError("Invalid credentials");
                                }
                            });
                        }
                    }
                }>
                    Submit
                </Button>
            </Form>
        </Container>
    );
}

export default Login;

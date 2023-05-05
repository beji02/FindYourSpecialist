import React from "react";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import { RegisterFormDto } from "./domain/dto/registerFormDto";
function Signup() {
    const [registerForm, setRegisterForm] = React.useState(new RegisterFormDto());
    const [password, setPassword] = React.useState('');
    const [error, setError] = React.useState('');

    const handleUpdate = (event) => {
        let name = event.target.name
        let value = event.target.value
        setRegisterForm({...registerForm, [name]:value})
    }

    return (
        <Container className="d-flex text-center justify-content-center align-items-center"
                   style={{ minHeight: "100vh" }}>
            <Form>
                {error && <p className="text-danger">{error}</p>}
                <h1 className="mb-3">Sign up</h1>
                <Form.Group className="mb-3" controlId="firstName">
                    <Form.Control type="text" name={'firstName'} placeholder="Enter First Name" value={registerForm.firstName}
                        onChange={handleUpdate}
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="lastName">
                    <Form.Control type="text" name={'lastName'} placeholder="Enter Last Name" value={registerForm.lastName}
                                  onChange={handleUpdate}
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="username">
                    <Form.Control type="text" name={'username'} placeholder="Enter Username" value={registerForm.username}
                        onChange={handleUpdate}
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="password">
                    <Form.Control type="password" name={'password'} placeholder="Enter Password" value={registerForm.password}
                        onChange={handleUpdate}
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="password1">
                    <Form.Control type="password" placeholder="Enter Password again" value={password}
                        onChange={(event) => {
                            setPassword(event.target.value);
                        }}
                    />
                </Form.Group>
                <Button variant="primary" type="submit" onClick={
                    async (event) => {
                        event.preventDefault();
                        if (registerForm.firstName === "" || registerForm.lastName === "" || registerForm.username === "" || registerForm.password === "") {
                            setError("Please fill all the details");
                        } else if (registerForm.password !== password) {
                            setError("Passwords do not match");
                            console.log(registerForm.password, password);
                        } else {
                            await fetch('signup', {
                                method: 'POST',
                                headers: {
                                    'Accept': 'application/json',
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(registerForm),
                            }).then((response) => {
                                if (response.status === 200) {
                                    window.location.href = "/";
                                } else {
                                    setError("Server error");
                                }
                            });
                        }
                    }
                }

                >
                    Submit
                </Button>
            </Form>
        </Container>
    );
}

export default Signup;

import React from "react";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
function Login() {
    return (
        <Container className="d-flex text-center justify-content-center align-items-center"
                   style={{ minHeight: "100vh" }}>
            <Form>
                <h1 className="mb-3">Login</h1>
                <Form.Group className="mb-3" controlId="username">
                    <Form.Control type="text" placeholder="Enter Username" />
                </Form.Group>

                <Form.Group className="mb-3" controlId="password">
                    <Form.Control type="password" placeholder="Enter Password" />
                </Form.Group>
                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </Container>
    );
}

export default Login;

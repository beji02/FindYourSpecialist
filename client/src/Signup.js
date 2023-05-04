import React from "react";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
function Signup() {
    return (
        <Container className="d-flex text-center justify-content-center align-items-center"
                   style={{ minHeight: "100vh" }}>
            <Form>
                <h1 className="mb-3">Sign up</h1>
                <Form.Group className="mb-3" controlId="firstName">
                    <Form.Control type="text" placeholder="Enter First Name" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="lastName">
                    <Form.Control type="text" placeholder="Enter Username" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="password">
                    <Form.Control type="password" placeholder="Enter Password" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="password">
                    <Form.Control type="password" placeholder="Enter Password again" />
                </Form.Group>
                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </Container>
    );
}

export default Signup;

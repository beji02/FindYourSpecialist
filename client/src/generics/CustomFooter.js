import { Container, Navbar } from "react-bootstrap";
import Nav from "react-bootstrap/Nav";
import React from "react";
function CustomFooter() {
    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Container>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="#ourpolicy">Our policy</Nav.Link>
                    </Nav>
                    <Nav className="ml-auto">
                        <Nav.Link href="#contactus">Built with &#x2661; by XTeam</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default CustomFooter;

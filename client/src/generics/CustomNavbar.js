import React from "react";
import Container from 'react-bootstrap/Container';
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
function CustomNavbar() {
    return (
        <Navbar bg="light" expand="lg">
            <Container>
                <Navbar.Brand>
                    <img
                        src="logo.svg"
                        alt="Logo"
                        width="40"
                        height="40"
                        className="d-inline-block align-top"
                    />
                    {' '}
                    Find your specialist
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/home">Home</Nav.Link>
                        <Nav.Link href="/addannouncement">Add your announcement</Nav.Link>
                        <Nav.Link href="/myannouncements">My announcements</Nav.Link>
                    </Nav>
                    <Nav className="ml-auto">
                        <Nav.Link href="/profile">Profile</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default CustomNavbar;
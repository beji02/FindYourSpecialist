import React, { useEffect } from "react";
import Container from 'react-bootstrap/Container';
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import { useNavigate } from "react-router-dom";

function CustomNavbar() {
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");
    });

    const handleProfileClick = () => {
        navigate("/profile")
    };

    const handleHomeClick = () => {
        navigate("/home");
    }

    return (
        <Navbar bg="light" expand="lg">
            <Container>
                <Navbar.Brand>
                    <Nav.Link onClick={handleHomeClick}>Find your specialist</Nav.Link>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/home">Home</Nav.Link>
                        <Nav.Link href="/addannouncement">Add your announcement</Nav.Link>
                        <Nav.Link href="/myannouncements">My announcements</Nav.Link>
                    </Nav>
                    <Nav className="ml-auto">
                        <Nav.Link onClick={handleProfileClick}>Profile</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default CustomNavbar;

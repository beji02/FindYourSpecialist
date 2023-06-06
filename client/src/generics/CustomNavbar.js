import React, {useEffect, useState} from "react";
import Container from 'react-bootstrap/Container';
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import { useNavigate } from "react-router-dom";
import {isLogged} from "../utils/utils";
import {logout} from "../utils/restcalls/user";

function CustomNavbar({isSpecialist}) {
    const navigate = useNavigate();

    const [logged, setLogged] = useState(false);

    useEffect(() => {
        setLogged(isLogged());
    });

    const handleProfileClick = () => {
        navigate("/profile")
    };

    const handleHomeClick = () => {
        navigate("/home");
    }

    const handleLogOut = () => {
        logout();
        setLogged(isLogged());
        navigate('/home');
    }

    const handleLogIn = () => {
        navigate("/login");
    }

    const handleNotificationsClick = () => {
        navigate('/notifications')
    }

    const handleMyAnnouncementsClick = () => {
        navigate('/myannouncements');
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
                        {logged && isSpecialist && <Nav.Link href="/addannouncement">Add your announcement</Nav.Link>}
                        {logged && isSpecialist && <Nav.Link onClick={handleMyAnnouncementsClick}>My announcements</Nav.Link>}
                        {logged && isSpecialist && <Nav.Link onClick={handleNotificationsClick}>Notifications</Nav.Link>}
                    </Nav>
                    <Nav className="ml-auto">
                        {!logged && <Nav.Link onClick={handleLogIn}>Log in</Nav.Link>}
                        {logged && <Nav.Link onClick={handleLogOut}>Log out</Nav.Link>}
                        {logged && <Nav.Link onClick={handleProfileClick}>Profile</Nav.Link>}

                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default CustomNavbar;

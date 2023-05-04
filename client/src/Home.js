import NavbarHome from "./NavbarHome";
import Container from "react-bootstrap/Container";
import React from "react";
import FooterHome from "./FooterHome";
function Home() {
    return (
        <Container>
            <NavbarHome />
            <Container className="fixed-bottom">
                <FooterHome />
            </Container>
        </Container>
    );
}

export default Home;
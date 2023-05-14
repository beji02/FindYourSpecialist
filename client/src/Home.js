import NavbarHome from "./NavbarHome";
import Container from "react-bootstrap/Container";
import React from "react";
import FooterHome from "./FooterHome";
import SearchBar from "./SearchBar";

function Home() {
    return (
        <Container>
            <Container className="top">
                <NavbarHome />
            </Container>
            <Container className="mt-3">
                <SearchBar/>
            </Container>
            <Container className="bottom">
                <FooterHome />
            </Container>
        </Container>
    );
}
export default Home;
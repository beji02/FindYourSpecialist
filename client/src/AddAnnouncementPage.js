import NavbarHome from "./NavbarHome";
import Container from "react-bootstrap/Container";
import React, {useEffect} from "react";
import FooterHome from "./FooterHome";
import { useNavigate } from "react-router-dom";
import SearchBar from "./SearchBar";
import AddAnnouncementForm from "./AddAnnouncementForm";
function Home() {
    useEffect(() => {
        const token = localStorage.getItem("token");
    });

    return (
        <Container>
            <NavbarHome />
            <AddAnnouncementForm />
            <Container className="bottom">
                <FooterHome />
            </Container>
        </Container>
    );
}

export default Home;
import NavbarHome from "./NavbarHome";
import Container from "react-bootstrap/Container";
import React from "react";
import FooterHome from "./FooterHome";
import SearchBar from "./SearchBar";
import { useEffect, useState } from "react";

function Home() {
    const [announcements, setAnnouncements] = useState([]);

    useEffect(() => {
        // Fetch announcements from the server
        fetch("announcements", {
            method: "GET",
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
            },
        })
            .then((response) => response.json())
            .then((data) => setAnnouncements(data))
            .catch((error) => {
                console.error("Error fetching announcements:", error);
            });
    }, []);

    return (
        <Container>
            <Container className="top">
                <NavbarHome />
            </Container>
            <Container className="mt-3">
                <SearchBar announcements={announcements} />
            </Container>
            <Container className="bottom">
                <FooterHome />
            </Container>
        </Container>
    );
}
export default Home;
import Container from "react-bootstrap/Container";
import React from "react";
import AddAnnouncementForm from "./AddAnnouncementForm";
import CustomFooter from "../generics/CustomFooter";
import CustomNavbar from "../generics/CustomNavbar";
function Home() {

    return (
        <Container>
            <CustomNavbar />
            <AddAnnouncementForm />
            <Container className="bottom">
                <CustomFooter />
            </Container>
        </Container>
    );
}

export default Home;
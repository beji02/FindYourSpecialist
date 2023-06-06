import Container from "react-bootstrap/Container";
import AnnouncementsPage from "./AnnouncementsPage";
import CustomNavbar from "./generics/CustomNavbar";
import {isSpecialist} from "./utils/roles";
import React from "react";

const MyAnnouncementsPage = () => {
    return (
        <Container>
            <CustomNavbar isSpecialist={isSpecialist(localStorage.getItem("roles"))}/>
            <AnnouncementsPage includeToken={true} page={1}/>
        </Container>
    );
};

export default MyAnnouncementsPage;

import Container from "react-bootstrap/Container";
import React, {useEffect, useState} from "react";
import AnnouncementsPage from "../AnnouncementsPage";
import CustomNavbar from "../generics/CustomNavbar";
import {isSpecialist} from "../utils/roles";

function HomePage() {

    useEffect(() => {
        const token = localStorage.getItem("token");
        console.log(token);
    });

    return (
        <Container>
            <CustomNavbar isSpecialist={isSpecialist(localStorage.getItem("roles"))}/>
            <AnnouncementsPage includeToken={true} page={0}/>
        </Container>
    );
}

export default HomePage;
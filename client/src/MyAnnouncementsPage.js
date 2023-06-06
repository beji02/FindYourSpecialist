import Container from "react-bootstrap/Container";
import AnnouncementsPage from "./AnnouncementsPage";
import AnnouncementContext from "./home/announcement/AnnouncementContext"
import CustomNavbar from "./generics/CustomNavbar";
import {isSpecialist} from "./utils/roles";
import React from "react";

const MyAnnouncementsPage = () => {
    return (
        <Container>
            <CustomNavbar isSpecialist={isSpecialist(localStorage.getItem("roles"))}/>
            <AnnouncementContext.Provider value={true}>
                <AnnouncementsPage includeToken={true} page={1}/>
            </AnnouncementContext.Provider>
        </Container>
    );
};

export default MyAnnouncementsPage;

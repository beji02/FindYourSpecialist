import Container from "react-bootstrap/Container";
import React, {useEffect, useState} from "react";
import AnnouncementsPage from "../AnnouncementsPage";

function HomePage() {
    return (
        <Container>
            <AnnouncementsPage includeToken={false}/>
        </Container>
    );
}

export default HomePage;
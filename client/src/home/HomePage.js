import Container from "react-bootstrap/Container";
import React, {useEffect, useState} from "react";
import AnnouncementsPage from "../AnnouncementsPage";

function HomePage() {
    return (
        <Container>
            <AnnouncementsPage includeToken={true} page={0}/>
        </Container>
    );
}

export default HomePage;
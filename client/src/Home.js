import NavbarHome from "./NavbarHome";
import Container from "react-bootstrap/Container";
import React from "react";
import FooterHome from "./FooterHome";
import AnnouncementList from "./AnnouncementList";
function Home() {
    const announcements = [
        {
            id: 1,
            title: "Announcement 1",
            description: "This is the first announcement",
            startDate: "2022-05-01",
            endDate: "2022-05-31",
            image: "https://via.placeholder.com/150",
            rating: 3.5,
        },
        {
            id: 2,
            title: "Announcement 2",
            description: "This is the second announcement",
            startDate: "2023-05-01",
            endDate: "2023-05-31",
            image: "https://via.placeholder.com/150",
            rating: 4.9,
        },
        // add more announcements here
    ];
    return (
        <Container>
            <NavbarHome />
            <AnnouncementList announcements={announcements} />
            <Container className="fixed-bottom">
                <FooterHome />
            </Container>
        </Container>
    );
}

export default Home;
import NavbarHome from "./NavbarHome";
import Container from "react-bootstrap/Container";
import React, {useEffect} from "react";
import FooterHome from "./FooterHome";
import { useNavigate } from "react-router-dom";
import SearchBar from "./SearchBar";
function Home() {
    useEffect(() => {
        const token = localStorage.getItem("token");
    });

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
        {
            id: 3,
            title: "Announcement 3",
            description: "This is the third announcement",
            startDate: "2023-05-01",
            endDate: "2023-05-31",
            image: "https://via.placeholder.com/150",
            rating: 4.9,
        },
        {
            id: 4,
            title: "Announcement 4",
            description: "This is the forth announcement",
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
            <SearchBar announcements={announcements} />

            <Container className="fixed-bottom">
                <FooterHome />
            </Container>
        </Container>
    );
}

export default Home;
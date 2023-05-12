import NavbarHome from "./NavbarHome";
import Container from "react-bootstrap/Container";
import React from "react";
import FooterHome from "./FooterHome";
import SearchBar from "./SearchBar";
function Home() {
    // fetch announcements from the server using the fetch API
    let announcements = [];

    // await fetch('announcements', {
    //     method: 'GET',
    //     headers: {
    //         'Accept': 'application/json',
    //         'Content-Type': 'application/json'
    //     },
    // }).then((response) => {
    //     if (response.status === 200) {
    //         announcements.push(response.json());
    //     } else {
    //         console.log("Error");
    //     }
    // });

    // const announcements = [
    //     {
    //         id: 1,
    //         title: "Announcement 1",
    //         description: "This is the first announcement",
    //         startDate: "2022-05-01",
    //         endDate: "2022-05-31",
    //         image: "https://via.placeholder.com/150",
    //         rating: 3.5,
    //     },
    //     {
    //         id: 2,
    //         title: "Announcement 2",
    //         description: "This is the second announcement",
    //         startDate: "2023-05-01",
    //         endDate: "2023-05-31",
    //         image: "https://via.placeholder.com/150",
    //         rating: 4.9,
    //     },
    //     {
    //         id: 3,
    //         title: "Announcement 3",
    //         description: "This is the third announcement",
    //         startDate: "2023-05-01",
    //         endDate: "2023-05-31",
    //         image: "https://via.placeholder.com/150",
    //         rating: 4.9,
    //     },
    //     {
    //         id: 4,
    //         title: "Announcement 4",
    //         description: "This is the forth announcement",
    //         startDate: "2023-05-01",
    //         endDate: "2023-05-31",
    //         image: "https://via.placeholder.com/150",
    //         rating: 4.9,
    //     },
    //     // add more announcements here
    // ];
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
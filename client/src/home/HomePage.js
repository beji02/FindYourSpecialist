import Container from "react-bootstrap/Container";
import React, {useEffect, useState} from "react";
import AnnouncementsPage from "../AnnouncementsPage";
import { useNavigate } from "react-router-dom";
import CustomNavbar from "../generics/CustomNavbar";
import {isLogged} from "../utils/utils";
import {isSpecialist} from "../utils/roles";
import {getAnnouncements} from "../utils/restcalls/announcement";

function HomePage() {
    const navigate = useNavigate();
    const [announcements, setAnnouncements] = useState([]);
    const [selectedFilters, setSelectedFilters] = useState([]);
    const [searchQuery, setSearchQuery] = useState("");
    const [pageNumber, setPageNumber] = useState(0);
    const pageSize = 6;

    useEffect(() => {
        const token = localStorage.getItem("token");
        console.log(token);
    });

    const loadAnnouncements = () => {
        const filtersQueryParam = selectedFilters.map((filter) => filter.name.toLowerCase()).join(" ");

        getAnnouncements(searchQuery, pageNumber, pageSize, filtersQueryParam).then(data => {
            setAnnouncements(data);
        }).catch(error => console.log("Error fetching announcements", error));
    };

    const searchAnnouncements = () => {
        setPageNumber(0);
        loadAnnouncements();
    }

    useEffect(() => {
        loadAnnouncements();
    }, [pageNumber, pageSize]);

    return (
        <Container>
            <CustomNavbar isSpecialist={isSpecialist(localStorage.getItem("roles"))}/>
            <AnnouncementsPage includeToken={true} page={0}/>
        </Container>
    );
}

export default HomePage;
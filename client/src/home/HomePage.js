import Container from "react-bootstrap/Container";
import React, {useEffect, useState} from "react";
import AnnouncementsPage from "../AnnouncementsPage";
import { useNavigate } from "react-router-dom";

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
        if (!token) {
            navigate('/login');
        }
    });

    const loadAnnouncements = () => {
        //fetch announcements base on search query and selected filters
        const filtersQueryParam = selectedFilters.map((filter) => filter.name.toLowerCase()).join(" ");
        const url = `announcements?search-query=${searchQuery}&page-number=${pageNumber}&page-size=${pageSize}&search-categories=${filtersQueryParam}`;
        fetch(url)
            .then((response) => response.json())
            .then((data) => {
                setAnnouncements(data);
            })
            .catch((error) => {
                console.error("Error fetching announcements:", error);
            });
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
            <AnnouncementsPage includeToken={true} page={0}/>
        </Container>
    );
}

export default HomePage;
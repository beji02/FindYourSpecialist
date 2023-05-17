import CustomNavbar from "../generics/CustomNavbar";
import Container from "react-bootstrap/Container";
import React, {useEffect, useState} from "react";
import CustomFooter from "../generics/CustomFooter";
import SearchBar from "./SearchBar";
import QuickAccess from "./QuickAccess";
import Filters from "./Filters";
import AnnouncementList from "./announcement/AnnouncementList";
import CustomPagination from "./CustomPagination";
import {Col, Row} from "react-bootstrap";

function HomePage() {
    const [announcements, setAnnouncements] = useState([]);
    const [selectedFilters, setSelectedFilters] = useState([]);
    const [searchQuery, setSearchQuery] = useState("");
    const [pageNumber, setPageNumber] = useState(0);
    const pageSize = 6;

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
            <CustomNavbar/>
            <Container>
                <Row>
                    <Col xs={2} lg={2}>
                        <Container style={{ marginTop: '100px' }}>
                            <Filters setSelectedFilters={setSelectedFilters}/>
                        </Container>
                    </Col>
                    <Col xs={8} lg={8}>
                        <SearchBar searchAnnouncements={searchAnnouncements} setSearchQuery={setSearchQuery}/>
                        <AnnouncementList announcements={announcements}/>
                        <CustomPagination announcementsListLength={announcements.length} pageSize={pageSize} pageNumber={pageNumber} setPageNumber={setPageNumber}/>
                    </Col>
                    <Col xs={2} lg={2}>
                        <QuickAccess/>
                    </Col>
                </Row>
            </Container>
            <CustomFooter/>
        </Container>
    );
}

export default HomePage;
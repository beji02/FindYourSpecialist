import React, {useEffect, useState} from "react";
import {Col, Container, Row} from "react-bootstrap";
import AnnouncementList from "./home/announcement/AnnouncementList";
import CustomFooter from "./generics/CustomFooter";
import CustomNavbar from "./generics/CustomNavbar";
import CustomPagination from "./home/CustomPagination";
import Filters from "./home/Filters";
import QuickAccess from "./home/QuickAccess";
import SearchBar from "./home/SearchBar";

function AnnouncementsPage({...props}) {
    const [announcements, setAnnouncements] = useState([]);
    const [selectedFilters, setSelectedFilters] = useState([]);
    const [searchQuery, setSearchQuery] = useState("");
    const [pageNumber, setPageNumber] = useState(0);
    const pageSize = 6;

    const loadAnnouncements = () => {
        const filtersQueryParam = selectedFilters
            .map((filter) => filter.name.toLowerCase())
            .join(" ");
        let url = "";
        if (props.page === 0) {
            console.log("page home");
            url = `announcements?search-query=${searchQuery}&page-number=${pageNumber}&page-size=${pageSize}&search-categories=${filtersQueryParam}`;
        } else if (props.page === 1) {
            console.log("page my announcements");
            url = `myannouncements?search-query=${searchQuery}&page-number=${pageNumber}&page-size=${pageSize}&search-categories=${filtersQueryParam}`;
        }
        if (props.includeToken) {
            console.log("include token");
            const token = localStorage.getItem("token");
            // Include token in headers
            fetch(url, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            })
                .then((response) => response.json())
                .then((data) => {
                    setAnnouncements(data);
                })
                .catch((error) => {
                    console.error("Error fetching announcements:", error);
                });
        } else {
            fetch(url)
                .then((response) => response.json())
                .then((data) => {
                    setAnnouncements(data);
                })
                .catch((error) => {
                    console.error("Error fetching announcements:", error);
                });
        }
    };

    const searchAnnouncements = () => {
        setPageNumber(0);
        loadAnnouncements();
    };

    useEffect(() => {
        loadAnnouncements();
    }, [pageNumber, pageSize]);

    return (
        <Container>
            <CustomNavbar/>
            <Container>
                <Row>
                    <Col xs={2} lg={2}>
                        <Container style={{marginTop: "100px"}}>
                            <Filters setSelectedFilters={setSelectedFilters}/>
                        </Container>
                    </Col>
                    <Col xs={8} lg={8}>
                        <SearchBar
                            searchAnnouncements={searchAnnouncements}
                            setSearchQuery={setSearchQuery}
                        />
                        <AnnouncementList announcements={announcements}/>
                        <CustomPagination
                            announcementsListLength={announcements.length}
                            pageSize={pageSize}
                            pageNumber={pageNumber}
                            setPageNumber={setPageNumber}
                        />
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

export default AnnouncementsPage;

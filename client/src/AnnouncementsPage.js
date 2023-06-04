import React, {useEffect, useState} from "react";
import {Col, Container, Row} from "react-bootstrap";
import AnnouncementList from "./home/announcement/AnnouncementList";
import CustomFooter from "./generics/CustomFooter";
import CustomNavbar from "./generics/CustomNavbar";
import CustomPagination from "./home/CustomPagination";
import Filters from "./home/Filters";
import QuickAccess from "./home/QuickAccess";
import SearchBar from "./home/SearchBar";
import {isLogged} from "./utils/utils";
import {getAnnouncements, getMyAnnouncements} from "./utils/restcalls/announcement";

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
        if (props.page === 0) {
            console.log("page home");
            getAnnouncements(searchQuery, pageNumber, pageSize, filtersQueryParam).then(data => {
                setAnnouncements(data);
            }).catch(error => console.log("Error fetching announcements", error));
        } else if (props.page === 1) {
            console.log("page my announcements");
            getMyAnnouncements(searchQuery, pageNumber, pageSize, filtersQueryParam).then(data => {
                setAnnouncements(data);
            }).catch(error => console.log("Error fetching announcements", error));
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

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
import AnnouncementsPage from "../AnnouncementsPage";

function HomePage() {
    return (
        <Container>
            <AnnouncementsPage includeToken={false}/>
        </Container>
    );
}

export default HomePage;
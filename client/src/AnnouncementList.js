import React from "react";
import AnnouncementCard from "./AnnouncementCard";
import { Container, Row, Col } from "react-bootstrap";

function AnnouncementList(props) {
    const { announcements } = props;

    const containerStyle = {
        display: "flex",
        flexWrap: "wrap",
        gap: "1rem",
    };

    return (
        <Container style={containerStyle}>
            {announcements.map((announcement) => (
                <Col md={6} lg={3} key={announcement.id}>
                    <AnnouncementCard {...announcement} />
                </Col>
            ))}
        </Container>
    );
}

export default AnnouncementList;

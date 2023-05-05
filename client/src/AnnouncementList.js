import React from "react";
import AnnouncementCard from "./AnnouncementCard";
import { Container, Row, Col } from "react-bootstrap";

function AnnouncementList(props) {
    const { announcements } = props;

    const firstTenAnnouncements = announcements.slice(0, 10);

    return (
        <Container>
            <Row>
                {firstTenAnnouncements.map((announcement) => (
                    <Col md={6} lg={4} key={announcement.id}>
                        <AnnouncementCard {...announcement} />
                    </Col>
                ))}
            </Row>
        </Container>
    );
}

export default AnnouncementList;

import React from 'react';
import { Container, Row, Col, Card } from 'react-bootstrap';
import AnnouncementCard from './AnnouncementCard';

function AnnouncementsTile({ title, announcements }) {
    return (
        <Container className="mt-3">
            <Row className="justify-content-center">
                <Col md={8}>
                    <h2>{title}</h2>
                    <Card>
                        <Card.Body>
                            {announcements.length === 0 ? (
                                <p className="text-center text-muted">No announcements available.</p>
                            ) : (
                                <>
                                    {announcements.map((announcement) => (
                                        <Row key={announcement.id} className="mb-3">
                                            <Col>
                                                <AnnouncementCard announcement={announcement} />
                                            </Col>
                                        </Row>
                                    ))}
                                </>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
}

export default AnnouncementsTile;

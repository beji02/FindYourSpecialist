import React from 'react';
import { Row, Col } from 'react-bootstrap';
import AnnouncementCard from './AnnouncementCard';

function AnnouncementsTile({ title, announcements }) {
    return (
        <Row>
            <Col>
                <h2>{title}</h2>
            </Col>
            <Col>
                <div className="row row-cols-3">
                    {announcements.map((announcement) => (
                        <AnnouncementCard key={announcement.id} announcement={announcement} />
                    ))}
                </div>
            </Col>
        </Row>
    );
}

export default AnnouncementsTile;

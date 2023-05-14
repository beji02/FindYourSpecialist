import React from "react";
import { Card, Col, Row } from "react-bootstrap";

function AnnouncementCard(props) {
    const { title, description, startDate, endDate, image, rate } = props;

    return (
        <Card className="mb-4 announcement" style={{ minWidth: "150px" }}>
            <Row>
                <Col md={4} className="text-center">
                    <Card.Img variant="top" src={image} />
                    <Card.Text>Current rating: {rate}/5 &#x2605;</Card.Text>
                </Col>
                <Col md={8}>
                    <Card.Body>
                        <Card.Title>{title}</Card.Title>
                        <Card.Text>{description}</Card.Text>
                    </Card.Body>
                    <Card.Footer className="text-end">
                        <small className="text-muted">
                            {startDate} - {endDate}
                        </small>
                    </Card.Footer>
                </Col>
            </Row>
        </Card>
    );
}

export default AnnouncementCard;

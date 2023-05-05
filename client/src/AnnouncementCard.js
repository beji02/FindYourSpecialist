import React from "react";
import { Card, Col, Row } from "react-bootstrap";

function AnnouncementCard(props) {
    const { title, description, startDate, endDate, image, rating } = props;

    return (
        <Card className="mb-4">
            <Row>
                <Col md={4} className="text-center">
                    <Card.Img variant="top" src={image} />
                    <Card.Text>Current rating: {rating}/5 &#x2605;</Card.Text>
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

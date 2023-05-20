import React, {useState} from "react";
import {Card, Col, Row} from "react-bootstrap";
import "../../style/AnnouncementCard.css"; // Import the CSS file for custom styling

function AnnouncementCard({announcement}) {
    const [isFavorite, setIsFavorite] = useState(announcement.favorite);
    const heartTitle = isFavorite ? 'Remove from favorites' : 'Add to favorites';

    const handleFavoriteClick = () => {
        const requestOptions = {
            method: !isFavorite ? "PUT" : "DELETE",
            headers: {
                Authorization: `Bearer ${localStorage.token}`,
                "Content-Type": "application/json",
            },
        };

        const url = `/favourites/${announcement.id}`;

        fetch(url, requestOptions)
            .then(response => {
                if (response.ok) {
                    if (!isFavorite) {
                        console.log("Announcement added to favorites");
                    } else {
                        console.log("Announcement removed from favorites");
                    }
                } else {
                    throw new Error("Request failed");
                }
            })
            .catch(error => {
                console.error("Failed to toggle announcement favorite", error);
            });

        setIsFavorite(!isFavorite);
    };

    return (
        <Card className="mb-4 announcement" style={{width: "400px", margin: "10px"}}>
            <Row>
                <Col md={4} className="text-center">
                    <Card.Img variant="top" src="data:image/png;base64,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"/>
                    <Card.Text>
                        Current rating: {announcement.rate}/5 &#x2605;
                    </Card.Text>
                </Col>
                <Col md={8}>
                    <Card.Body>
                        <Card.Title>
                            {announcement.title}{" "}
                            <span
                                className={`heart-icon ${isFavorite ? "favorite" : ""}`}
                                onClick={handleFavoriteClick}
                                title = {heartTitle}
                            >
                &#9825;
              </span>
                        </Card.Title>
                        <Card.Text>{announcement.description}</Card.Text>
                    </Card.Body>
                    <Card.Footer className="text-end">
                        <small className="text-muted">
                            {announcement.startDate} - {announcement.endDate}
                        </small>
                    </Card.Footer>
                </Col>
            </Row>
        </Card>
    );
}

export default AnnouncementCard;
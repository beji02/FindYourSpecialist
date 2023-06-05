import React, { useState } from 'react';
import { Card } from 'react-bootstrap';
import { markNotificationAsRead } from "../../utils/restcalls/user";

function NotificationCard({ notification }) {
    const { id, title, text, date, read } = notification;
    const [isRead, setIsRead] = useState(read);

    const cardStyle = {
        backgroundColor: !isRead ? '#b8d6d9' : '#f9f9f9',
        overflow: 'hidden',
        maxHeight: '80px',
        fontSize: '14px',
        padding: '10px',
        marginBottom: '10px',
    };

    const formattedDate = new Date(date).toLocaleString(undefined, {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        hour12: false,
        formatMatcher: 'basic',
    });

    const handleClick = () => {
        if (!isRead) {
            markNotificationAsRead(id)
                .then(r => console.log("Marked notification as read"))
                .catch(e => console.log("Failed to mark notification as read"));
            setIsRead(true);
        }
    };

    return (
        <Card style={cardStyle} onClick={handleClick}>
            <Card.Body>
                <Card.Title>{title}</Card.Title>
                <Card.Text>{text}</Card.Text>
                <p>Date: {formattedDate}</p>
            </Card.Body>
        </Card>
    );
}

export default NotificationCard;

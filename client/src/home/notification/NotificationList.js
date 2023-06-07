import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import NotificationCard from './NotificationCard';
import CustomNavbar from "../../generics/CustomNavbar";
import { isSpecialist } from "../../utils/roles";

function NotificationList({ notifications }) {
    return (
        <Container className="mt-3">
            <Row>
                <Col md={8} className="mx-auto">
                    <h2>Notifications</h2>
                    {notifications.length === 0 ? (
                        <p className="text-center text-muted">No notifications available.</p>
                    ) : (
                        notifications.map((notification) => (
                            <NotificationCard key={notification.id} notification={notification} />
                        ))
                    )}
                </Col>
            </Row>
        </Container>
    );
}

export default NotificationList;

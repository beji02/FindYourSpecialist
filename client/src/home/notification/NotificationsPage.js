import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Alert } from 'react-bootstrap';
import NotificationCard from './NotificationCard';
import CustomNavbar from "../../generics/CustomNavbar";
import { isSpecialist } from "../../utils/roles";
import NotificationList from "./NotificationList";
import { getNotifications } from "../../utils/restcalls/user";
import { Client } from '@stomp/stompjs';

function NotificationPage() {
    const [notifications, setNotifications] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        getNotifications()
            .then(data => {
                setNotifications(data);
            })
            .catch(error => {
                setError('Error retrieving notifications.');
                console.error('Error retrieving notifications:', error);
            });

        const client = new Client();
        client.brokerURL = 'ws://localhost:8080/ws';
        client.onConnect = function (frame) {
            console.log('Connected to WebSocket');
            client.subscribe('/app/topic/reservations', function (message) {
                // Handle the notification message here
                // const notificationData = JSON.parse(message.body);
                console.log('New reservation:', message);
                // Perform any necessary operations with the received notification data
                // For example, update the notifications state with the new data
                // setNotifications(prevNotifications => [...prevNotifications, notificationData]);
            });
        };
        client.activate();

        return () => {
            client.deactivate();
        };
    }, []);

    return (
        <>
            <CustomNavbar isSpecialist={isSpecialist(localStorage.getItem("roles"))} />
            {error && (
                <Alert variant="danger" className="mt-3">
                    {error}
                </Alert>
            )}
            <NotificationList notifications={notifications} />
        </>
    );
}

export default NotificationPage;

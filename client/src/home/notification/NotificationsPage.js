import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Alert } from 'react-bootstrap';
import NotificationCard from './NotificationCard';
import CustomNavbar from "../../generics/CustomNavbar";
import { isSpecialist } from "../../utils/roles";
import NotificationList from "./NotificationList";
import { getNotifications } from "../../utils/restcalls/user";

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

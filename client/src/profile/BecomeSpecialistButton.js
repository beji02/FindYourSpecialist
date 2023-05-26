import React, {useEffect, useState} from 'react';
import { Container, Row, Col, Card, Button, Form, InputGroup } from 'react-bootstrap';
import 'react-phone-input-2/lib/style.css';
import './BecomeSpecialistButton.css';

const BecomeSpecialistButton = ({ token }) => {

    const [profileForm, setProfileForm] = useState({
        updateType: '',
    });

    useEffect(() => {
        setProfileForm({ ...profileForm, updateType: 'UPGRADE_TO_SPECIALIST' })
    }, []);

    const handleBecomeSpecialist = (event) => {
        event.preventDefault();

        console.log(profileForm.updateType);

        try {
            fetch('user', {
                method: 'PUT',
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                    Authorization: localStorage.getItem('token'),
                },
                body: JSON.stringify(profileForm),
            })
        } catch (error) {
            console.log("ERROR")
        }
    }

    return (
        <Col md={8} className="mx-auto">
            <Button variant="primary" className="w-100 mt-4 custom-button" onClick={handleBecomeSpecialist}>
                Become a specialist
            </Button>
        </Col>
    );
};

export default BecomeSpecialistButton;
import React, {useEffect, useState} from 'react';
import { Container, Row, Col, Card, Button, Form, InputGroup } from 'react-bootstrap';
import 'react-phone-input-2/lib/style.css';
import './BecomeSpecialistButton.css';

const BecomeSpecialistButton = ({ token, upgradeToSpecialistFunc }) => {

    const [profileForm, setProfileForm] = useState({
        updateType: '',
    });

    useEffect(() => {
        setProfileForm({ ...profileForm, updateType: 'UPGRADE_TO_SPECIALIST' })
    }, []);

    const handleBecomeSpecialist = (event) => {
        event.preventDefault();
        console.log(profileForm);
        upgradeToSpecialistFunc(profileForm);
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
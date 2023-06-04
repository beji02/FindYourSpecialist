import React, {useEffect, useState} from 'react';
import { Container, Row, Col, Card, Button, Form, InputGroup } from 'react-bootstrap';
import 'react-phone-input-2/lib/style.css';
import './BecomeSpecialistButton.css';

const BecomeSpecialistButton = ({ token }) => {

    const handleBecomeSpecialist = (event) => {
        event.preventDefault();

        try {
            fetch('specialists', {
                method: 'POST',
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                }
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
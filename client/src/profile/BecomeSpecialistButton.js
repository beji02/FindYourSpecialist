import React, {useEffect, useState} from 'react';
import { Container, Row, Col, Card, Button, Form, InputGroup } from 'react-bootstrap';
import 'react-phone-input-2/lib/style.css';
import './BecomeSpecialistButton.css';

const BecomeSpecialistButton = ({ token }) => {
    return (
        <Col md={8} className="mx-auto">
            <Button variant="primary" className="w-100 mt-4 custom-button">
                Become a specialist
            </Button>
        </Col>
    );
};

export default BecomeSpecialistButton;
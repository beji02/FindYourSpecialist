import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Card, Button, Form, InputGroup } from 'react-bootstrap';
import 'react-phone-input-2/lib/style.css';
import PhoneInput from 'react-phone-input-2';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import MySchedule from "../myschedule/MySchedule";

//AIzaSyBZCn63RlKrEgWS1TJdfE-L_DGUWYLDDt0 -- google maps api key

const SpecialistDataTile = ({ token }) => {
    const [specialistForm, setSpecialistForm] = useState({
        location: '',
        description: '',
    });

    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleUpdate = (event) => {
        const name = event.target.name;
        const value = event.target.value;

        setSpecialistForm({ ...specialistForm, [name]: value });
    };

    const handleSave = (event) => {
        event.preventDefault();

        try {
            fetch('specialist', {
                method: 'PUT',
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                    Authorization: localStorage.getItem('token'),
                },
                body: JSON.stringify(specialistForm),
            })
                .then((response) => {
                    if (response.ok) {
                        setSuccess('Profile updated successfully');
                    } else {
                        setError('Invalid credentials');
                    }
                })
                .catch((error) => {
                    setError('An error occurred. Please try again.');
                });
        } catch (error) {
            setError('An error occurred. Please try again.');
        }
    };

    const [phone, setPhone] = useState('');

    useEffect(() => {
        try {
            fetch('specialist', {
                method: 'POST',
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                    Authorization: token,
                },
            })
                .then((response) => {
                    if (response.status == 200) {
                        return response.json();
                    }
                    throw new Error('Something went wrong');
                })
                .then((data) => {
                    setSpecialistForm({
                        location: data.location,
                        description: data.description,
                    });
                });
        } catch (error) {
            setError('An error occurred. Please try again.');
        }
    }, []);

    return (
        <Row className="justify-content-md-center mt-5">
            <Col md={8}>
                <h2>Work info</h2>
                <Card>
                    <Card.Body>
                        {error && <p className="text-danger">{error}</p>}
                        {success && <p className="text-success">{success}</p>}
                        <Form>
                            <Form.Group controlId="location">
                                <Form.Label>Location</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="location"
                                    onChange={handleUpdate}
                                    value={specialistForm.location}
                                    placeholder="Enter your location"
                                />
                            </Form.Group>
                            <Form.Group controlId="description">
                                <Form.Label>Description</Form.Label>
                                <Form.Control
                                    as="textarea"
                                    rows={3}
                                    name="description"
                                    onChange={handleUpdate}
                                    value={specialistForm.description}
                                    placeholder="Enter your description"
                                />
                            </Form.Group>
                            <Row className="justify-content-end">
                                <Col xs={6} md={4} lg={3}>
                                    <Button
                                        variant="primary"
                                        type="submit"
                                        onClick={handleSave}
                                        className="float-end mt-2"
                                    >
                                        Save Changes
                                    </Button>
                                </Col>
                            </Row>
                        </Form>
                    </Card.Body>
                </Card>
            </Col>
        </Row>
    );
};

export default SpecialistDataTile;

import React, { useEffect, useState } from 'react';
import { Row, Col, Card, Button, Form } from 'react-bootstrap';
import 'react-phone-input-2/lib/style.css';
import 'react-datepicker/dist/react-datepicker.css';
import { LoadScript, Autocomplete } from '@react-google-maps/api';
import { MAPS_API_KEY } from '../utils/constants'


const SpecialistDataTile = ({ token, updateWorkInfoFunc, getWorkInfoFunc }) => {
    const [specialistForm, setSpecialistForm] = useState({
        location: '',
        description: '',
    });

    const [autocomplete, setAutocomplete] = useState(null);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleUpdate = (event) => {
        const name = event.target.name;
        const value = event.target.value;

        setSpecialistForm({ ...specialistForm, [name]: value });
    };

    const handlePlaceSelect = () => {
        if (autocomplete !== null) {
            const place = autocomplete.getPlace();
            const location = place.formatted_address;
            setSpecialistForm({ ...specialistForm, location });
        }
    };

    const handleSave = (event) => {
        event.preventDefault();

        updateWorkInfoFunc(specialistForm).then(data => {
            setSuccess('Profile updated successfully');
        }).catch(error => {
            setError('An error occurred. Please try again.');
        })
    };

    useEffect(() => {
        getWorkInfoFunc()
            .then((data) => {
                setSpecialistForm({
                    location: data.location,
                    description: data.description,
                });
            }).catch((error) => {
            setError('An error occurred. Please try again.');
        });
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
                                <LoadScript googleMapsApiKey={MAPS_API_KEY} libraries={['places']}>
                                    <Autocomplete
                                        onLoad={(autocomplete) => setAutocomplete(autocomplete)}
                                        onPlaceChanged={handlePlaceSelect}
                                    >
                                        <Form.Control
                                            type="text"
                                            name="location"
                                            onChange={handleUpdate}
                                            value={specialistForm.location}
                                            placeholder="Enter your location"
                                        />
                                    </Autocomplete>
                                </LoadScript>
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

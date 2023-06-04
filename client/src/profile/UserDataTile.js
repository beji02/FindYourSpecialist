import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Card, Button, Form, InputGroup } from 'react-bootstrap';
import 'react-phone-input-2/lib/style.css';
import PhoneInput from 'react-phone-input-2';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const UserDataTile = ({ token, updatePersonalInfoFunc, getPersonalInfoFunc }) => {
    const [profileForm, setProfileForm] = useState({
        firstName: '',
        lastName: '',
        username: '',
        email: '',
        phoneNumber: '',
        birthDate: null,
        updateType: '',
    });

    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleUpdate = (event) => {
        const name = event.target.name;
        const value = event.target.value;

        setProfileForm({ ...profileForm, [name]: value });
    };

    const handleSave = (event) => {
        event.preventDefault();

        console.log(profileForm.updateType);

        updatePersonalInfoFunc(profileForm).then(data => {
            setSuccess('Profile updated successfully');
        }).catch(error => {
            setError('An error occurred. Please try again.');
        })
    };

    useEffect(() => {
        getPersonalInfoFunc()
            .then((data) => {
                setProfileForm({
                    firstName: data.firstName,
                    lastName: data.lastName,
                    username: data.username,
                    email: data.email,
                    phoneNumber: data.phoneNumber,
                    birthDate: data.birthDate ? new Date(data.birthDate) : null,
                });
            }).catch((error) => {
            setError('An error occurred. Please try again.');
        });
    }, []);

    const CustomDatePickerInput = ({ value, onClick }) => (
        <Form.Control type="text" value={value} onClick={onClick} readOnly />
    );

    return (
        <Row className="justify-content-md-center mt-5">
            <Col md={8}>
                <h2>Personal info</h2>
                <Card>
                    <Card.Body>
                        {error && <p className="text-danger">{error}</p>}
                        {success && <p className="text-success">{success}</p>}
                        <Form>
                            <Row>
                                <Col>
                                    <Form.Group controlId="firstName">
                                        <Form.Label>First Name</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name="firstName"
                                            onChange={handleUpdate}
                                            value={profileForm.firstName}
                                            placeholder="Enter your first name"
                                        />
                                    </Form.Group>
                                </Col>
                                <Col>
                                    <Form.Group controlId="lastName">
                                        <Form.Label>Last Name</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name="lastName"
                                            onChange={handleUpdate}
                                            value={profileForm.lastName}
                                            placeholder="Enter your last name"
                                        />
                                    </Form.Group>
                                </Col>
                            </Row>
                            <Form.Group controlId="username">
                                <Form.Label>Username</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="username"
                                    disabled={true}
                                    onChange={handleUpdate}
                                    value={profileForm.username}
                                    placeholder="Enter your username"
                                />
                            </Form.Group>
                            <Form.Group controlId="email">
                                <Form.Label>Email</Form.Label>
                                <Form.Control
                                    type="email"
                                    name="email"
                                    onChange={handleUpdate}
                                    value={profileForm.email}
                                    placeholder="Enter your email"
                                />
                            </Form.Group>
                            <Row>
                                <Col>
                                    <Form.Group controlId="birthDate">
                                        <Form.Label>Date of Birth</Form.Label>
                                        <br />
                                        <DatePicker
                                            value={profileForm.birthDate}
                                            selected={profileForm.birthDate}
                                            onChange={(date) =>
                                                setProfileForm({ ...profileForm, birthDate: date })
                                            }
                                            maxDate={new Date()}
                                            dateFormat="yyyy-MM-dd"
                                            customInput={<CustomDatePickerInput />}
                                        />
                                    </Form.Group>
                                </Col>
                                <Col>
                                    <Form.Group controlId="phoneNumber">
                                        <Form.Label>Phone Number</Form.Label>
                                        <InputGroup>
                                            <PhoneInput
                                                country={'ro'} // Set the default country
                                                value={profileForm.phoneNumber}
                                                onChange={(value) =>
                                                    handleUpdate({ target: { name: 'phoneNumber', value } })
                                                }
                                                inputProps={{
                                                    className: 'form-control',
                                                    style: { width: '100%' },
                                                }}
                                            />
                                        </InputGroup>
                                    </Form.Group>
                                </Col>
                            </Row>
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

export default UserDataTile;

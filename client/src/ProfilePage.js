import React from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';

const ProfilePage = () => {
    return (
        <Container>
            <Row className="justify-content-md-center mt-5">
                <Col md={8}>
                    <Card>
                        <Card.Body>
                            <Card.Title>John Doe</Card.Title>
                            <Card.Text>
                                Hello! My name is John Doe and I'm a software engineer. I love working on web development projects and learning new things.
                            </Card.Text>
                            <Button variant="primary">Edit Profile</Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
            <Row className="justify-content-md-center mt-5">
                <Col md={8}>
                    <Card>
                        <Card.Body>
                            <Card.Title>Skills</Card.Title>
                            <Card.Text>
                                <ul>
                                    <li>JavaScript</li>
                                    <li>React</li>
                                    <li>Node.js</li>
                                    <li>HTML/CSS</li>
                                </ul>
                            </Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
            <Row className="justify-content-md-center mt-5">
                <Col md={8}>
                    <Card>
                        <Card.Body>
                            <Card.Title>Experience</Card.Title>
                            <Card.Text>
                                <ul>
                                    <li>Software Engineer at Acme Inc. (2018-present)</li>
                                    <li>Full Stack Developer at XYZ Corp. (2015-2018)</li>
                                </ul>
                            </Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
}

export default ProfilePage;

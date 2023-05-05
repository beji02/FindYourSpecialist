import React, { useState } from "react";
import {Form, Button, Container} from "react-bootstrap";
import NavbarHome from "./NavbarHome";
import FooterHome from "./FooterHome";

function AnnouncementForm(props) {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [image, setImage] = useState(null);

    const handleTitleChange = (event) => {
        setTitle(event.target.value);
    };

    const handleDescriptionChange = (event) => {
        setDescription(event.target.value);
    };

    const handleStartDateChange = (event) => {
        setStartDate(event.target.value);
    };

    const handleEndDateChange = (event) => {
        setEndDate(event.target.value);
    };

    const handleImageChange = (event) => {
        setImage(event.target.files[0]);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const formData = new FormData();
        formData.append("title", title);
        formData.append("description", description);
        formData.append("startDate", startDate);
        formData.append("endDate", endDate);
        if (image) {
            formData.append("image", image);
        }
        props.onSubmit(formData);
        setTitle("");
        setDescription("");
        setStartDate("");
        setEndDate("");
        setImage(null);
    };

    return (
        <Container>
            <Container className="fixed-top">
                <NavbarHome/>
            </Container>
            <Container className="d-flex text-center justify-content-center align-items-center"
                       style={{ minHeight: "100vh" }}>
                <Form onSubmit={handleSubmit}>
                    <Form.Group controlId="formBasicTitle">
                        <Form.Label>Title</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Enter title"
                            value={title}
                            onChange={handleTitleChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="formBasicDescription">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={3}
                            placeholder="Enter description"
                            value={description}
                            onChange={handleDescriptionChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="formBasicStartDate">
                        <Form.Label>Start Date</Form.Label>
                        <Form.Control
                            type="date"
                            placeholder="Enter start date"
                            value={startDate}
                            onChange={handleStartDateChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="formBasicEndDate">
                        <Form.Label>End Date</Form.Label>
                        <Form.Control
                            type="date"
                            placeholder="Enter end date"
                            value={endDate}
                            onChange={handleEndDateChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="formBasicImage">
                        <Form.Label>Image</Form.Label>
                        <Form.Control type="file" onChange={handleImageChange} />
                    </Form.Group>

                    <Button  className="mt-3" variant="primary" type="submit">
                        Submit
                    </Button>
                </Form>
            </Container>


            <Container className="fixed-bottom">
                <FooterHome/>
            </Container>

        </Container>
    );
}

export default AnnouncementForm;

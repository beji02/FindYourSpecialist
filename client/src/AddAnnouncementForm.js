import React, {useState} from "react";
import {Form, Button, Container} from "react-bootstrap";

function AnnouncementForm(props) {
    const [formValues, setFormValues] = useState({
        title: "",
        description: "",
        startDate: "",
        endDate: "",
        image: null,
    });
    const [error, setError] = useState("");
    const [image, setImage] = useState(null);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormValues((prevValues) => ({
            ...prevValues,
            [name]: value,
        }));
    };

    const handleImageChange = (event) => {
        const file = event.target.files[0];
        setImage(file);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (formValues.title === "" || formValues.description === "") {
            setError("Please fill in all the details");
            return;
        }

        try {
            const formData = new FormData();
            formData.append("title", formValues.title);
            formData.append("description", formValues.description);
            formData.append("startDate", formValues.startDate);
            formData.append("endDate", formValues.endDate);
            if (formValues.image) {
                formData.append("image", formValues.image);
            }

            const response = await fetch("your-api-endpoint", {
                method: "POST",
                body: formData,
            });

            if (response.ok) {
                // Handle successful response
            } else {
                // Handle error response
            }
        } catch (error) {
            // Handle fetch error
        }

        setFormValues({
            title: "",
            description: "",
            startDate: "",
            endDate: "",
            image: null,
        });
    };

    return (
        <Container className="d-flex text-center justify-content-center align-items-center"
                   style={{minHeight: "100vh"}}>
            <Form onSubmit={handleSubmit}>
                {error && <p className="text-danger">{error}</p>}

                <Form.Group controlId="formBasicTitle">
                    <Form.Label>Title</Form.Label>
                    <Form.Control
                        type="text"
                        name="title"
                        placeholder="Enter title"
                        value={formValues.title}
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <Form.Group controlId="formBasicDescription">
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={3}
                        name="description"
                        placeholder="Enter description"
                        value={formValues.description}
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <Form.Group controlId="formBasicStartDate">
                    <Form.Label>Start Date</Form.Label>
                    <Form.Control
                        type="date"
                        name="startDate"
                        placeholder="Enter start date"
                        value={formValues.startDate}
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <Form.Group controlId="formBasicEndDate">
                    <Form.Label>End Date</Form.Label>
                    <Form.Control
                        type="date"
                        name="endDate"
                        placeholder="Enter end date"
                        value={formValues.endDate}
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <Form.Group controlId="formBasicImage">
                    <Form.Label>Image</Form.Label>
                    <Form.Control type="file" onChange={handleImageChange}/>
                </Form.Group>

                <Button className="mt-3" variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </Container>
    );

}

export default AnnouncementForm;
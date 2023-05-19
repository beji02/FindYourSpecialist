import React, {useEffect, useState} from "react";
import {Form, Button, Container} from "react-bootstrap";

function AnnouncementForm(props) {
    const [formValues, setFormValues] = useState({
        title: "",
        description: "",
        startDate: "",
        endDate: "",
        fieldId: 0
    });
    const [fields, setFields] = useState([]);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    useEffect(() => {
        fetch("announcements/fields")
            .then((response) => response.json())
            .then((data) => {
                setFields(data); // Set the fetched fields in the state
            })
            .catch((error) => {
                console.error("Error fetching fields:", error);
            });
    }, []);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormValues((prevValues) => ({
            ...prevValues,
            [name]: name === "fieldId" ? parseInt(value) : value,
        }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (formValues.title === "" || formValues.description === "" || formValues.fieldId === 0 || formValues.startDate === "" || formValues.endDate === ""){
            setError("Please fill in all the details");
            return;
        }
        try {
            const token = localStorage.getItem("token");
            const response = await fetch("announcements", {
                method: "POST",
                headers: {
                    Accept: "application/json",
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify(formValues)
            });


            if (response.ok) {
                setSuccess("Announcement added successfully");
            } else {
                setError("An error occurred. Please try again.");
            }
            console.log(JSON.stringify(formValues));

        } catch (error) {
            setError("An error occurred. Please try again.");
        }

        setFormValues({
            title: "",
            description: "",
            startDate: "",
            endDate: "",
            fieldId: 1
        });
    };

    return (
        <Container className="d-flex text-center justify-content-center align-items-center"
                   style={{minHeight: "100vh"}}>
            <Form onSubmit={handleSubmit}>
                {error && <p className="text-danger">{error}</p>}
                {success && <p className="text-success">{success}</p>}
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

                <Form.Group controlId="formFieldSelect">
                    <Form.Label>Select a field</Form.Label>
                    <Form.Control
                        as="select"
                        name="fieldId"
                        value={formValues.fieldId}
                        onChange={handleInputChange}
                    >
                        <option value="0">Select the field &#x25BC;</option>
                        {fields.map((field) => (
                            <option key={field.id} value={field.id}>
                                {field.name}
                            </option>
                        ))}
                    </Form.Control>
                </Form.Group>

                <Button className="mt-3" variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </Container>
    );

}

export default AnnouncementForm;
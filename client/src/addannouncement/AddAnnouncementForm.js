import React, {useEffect, useState} from "react";
import {Form, Button, Container} from "react-bootstrap";
import {Calendar, DateObject} from "react-multi-date-picker";
// https://shahabyazdi.github.io/react-multi-date-picker/colors/


function AnnouncementForm(props) {
    const [formValues, setFormValues] = useState({
        title: "",
        description: "",
        workDays: [],
        fieldId: 0
    });
    const [fields, setFields] = useState([]);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    useEffect(() => {
        fetch("fields")
            .then((response) => response.json())
            .then((data) => {
                setFields(data); // Set the fetched fields in the state
            })
            .catch((error) => {
                console.error("Error fetching fields:", error);
            });
    }, []);

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setFormValues((prevValues) => ({
            ...prevValues,
            [name]: name === "fieldId" ? parseInt(value) : value,
        }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (formValues.title === "" || formValues.description === "" || formValues.fieldId === 0 || formValues.workDays.length === 0) {
            setError("Please fill in all the details");
            return;
        }
        try {
            //console.log(formValues.workDays[0].toString());
            const formattedWorkDays = formValues.workDays.map((date) => {
                    let newDate = new Date(date.toString());
                    let newUTCDate = new Date(newDate.getTime() - newDate.getTimezoneOffset() * 60000);
                    //console.log(newUTCDate);
                    return newUTCDate;
                }
            );
            formValues.workDays = formattedWorkDays;
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
            //console.log(JSON.stringify(formValues));

        } catch (error) {
            setError("An error occurred. Please try again.");
        }

        setFormValues({
            title: "",
            description: "",
            workDays: [],
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

                <Form.Group controlId="formWorkDays">
                    <Form.Label>Work days</Form.Label><br/>
                    <Calendar
                        multiple
                        value={formValues.workDays}
                        onChange={(value) => {
                            handleInputChange({target: {name: "workDays", value: value}})
                        }}
                        minDate={new DateObject()}
                    />
                </Form.Group>
                <Form.Group controlId="formFieldSelect">
                    <Form.Label>Select a field</Form.Label>
                    <Form.Control
                        as="select"
                        name="fieldId"
                        value={formValues.fieldId}
                        onChange={handleInputChange}>
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
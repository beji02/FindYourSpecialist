import React from "react";
import {Form, Button} from "react-bootstrap";

function SearchBar({searchAnnouncements, setSearchQuery}) {

    const handleInputChange = (event) => {
        const { value } = event.target;
        setSearchQuery(value);
    };
    const handleSearch = (event) => {
        event.preventDefault();
        searchAnnouncements();
    };

    return (
        <Form className="d-flex" onSubmit={handleSearch}>
            <Form.Control
                className="mt-3 me-2"
                type="text"
                placeholder="Search announcements"
                onChange={handleInputChange}
            />
            <Button variant="primary" type="submit" className="mt-3" >
                Search
            </Button>
        </Form>
    );
}

export default SearchBar;
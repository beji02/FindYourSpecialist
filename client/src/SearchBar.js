import React, { useState } from "react";
import {Container, Form} from "react-bootstrap";
import AnnouncementList from "./AnnouncementList";

function SearchBar(props) {
    const [searchTerm, setSearchTerm] = useState("");

    const handleSearch = (event) => {
        setSearchTerm(event.target.value);
    };

    const filterAnnouncements = () => {
        return props.announcements.filter((announcement) => {
            const title = announcement.title.toLowerCase();
            const description = announcement.description.toLowerCase();
            const searchTermLower = searchTerm.toLowerCase();
            return (
                title.includes(searchTermLower) || description.includes(searchTermLower)
            );
        });
    };

    return (
        <Form>
            <Form.Control className="mt-3"
                type="text"
                placeholder="Search announcements"
                value={searchTerm}
                onChange={handleSearch}
            />
            <Container className="mt-3">
                <AnnouncementList announcements={filterAnnouncements()} />
            </Container>
        </Form>
    );
}

export default SearchBar;

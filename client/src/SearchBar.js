import React, { useState, useEffect } from "react";
import { Container, Form, Button } from "react-bootstrap";
import AnnouncementList from "./AnnouncementList";
import FilterButtons from "./FilterButtons";

function SearchBar() {
    const [searchTerm, setSearchTerm] = useState("");
    const [announcements, setAnnouncements] = useState([]);
    const [pageNumber, setPageNumber] = useState(0);
    const [fields, setFields] = useState([]);
    const [selectedFilters, setSelectedFilters] = useState([]);

    const handleSearch = (event) => {
        setSearchTerm(event.target.value);
        setPageNumber(0);
    };

    const fetchAnnouncements = () => {
        // create a string of the selected filters separated by spaces
        const filtersQueryParam = selectedFilters.join(" ");
        const url = `/announcements?search-query=${searchTerm}&page-number=${pageNumber}&page-size=10&search-categories=${filtersQueryParam}`;

        fetch(url, {
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setAnnouncements(data);
            })
            .catch((error) => {
                console.error("Error fetching announcements:", error);
            });
    };

    const goToNextPage = () => {
        if (announcements.length === 10) {
            setPageNumber(pageNumber + 1);
        }
    };

    const goToPreviousPage = () => {
        if (pageNumber > 0) {
            setPageNumber(pageNumber - 1);
        }
    };

    useEffect(() => {
        fetchAnnouncements();
    }, [searchTerm, pageNumber, selectedFilters]);

    useEffect(() => {
        fetch("announcements/fields")
            .then((response) => response.json())
            .then((data) => {
                setFields(data);
            })
            .catch((error) => {
                console.error("Error fetching fields:", error);
            });
    }, []);

    const handleFilterSelect = (selected) => {
        setSelectedFilters(selected);
        console.log(selected);
    };

    return (
        <div className="mb-5 d-flex">
            <Form>
                <Form.Control
                    className="mt-3"
                    type="text"
                    placeholder="Search announcements"
                    value={searchTerm}
                    onChange={handleSearch}
                />
                <Container className="mt-3 d-flex">
                    <div className="flex-grow-1 flex-lg-grow-0">
                        <FilterButtons fields={fields} onSelect={handleFilterSelect} />
                    </div>
                    <div className="flex-grow-2 flex-lg-grow-1 ms-lg-3">
                        <AnnouncementList announcements={announcements} />
                    </div>
                </Container>
                <div className="d-flex justify-content-center mt-3">
                    <Button variant="primary" onClick={goToPreviousPage}>
                        Previous Page
                    </Button>{" "}
                    <Button variant="primary" onClick={goToNextPage} className="ms-2">
                        Next Page
                    </Button>
                </div>
            </Form>
        </div>
    );
}

export default SearchBar;

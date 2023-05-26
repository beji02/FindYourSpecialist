import React, {useEffect, useState} from "react";
import {Form} from "react-bootstrap";

function Filters({setSelectedFilters}) {
    const [filters, setFilters] = useState([]);

    useEffect(() => {
        fetch("fields")
            .then((response) => response.json())
            .then((data) => {
                setFilters(data);
            })
            .catch((error) => {
                console.error("Error fetching fields:", error);
            });
    }, []);


    function handleFilterSelect(filter) {
        setSelectedFilters(prevState => {
            const updatedFilters = [...prevState];

            if (updatedFilters.includes(filter)) {
                // Filter already selected, remove it
                updatedFilters.splice(updatedFilters.indexOf(filter), 1);
            } else {
                // Filter not selected, add it
                updatedFilters.push(filter);
            }

            console.log(updatedFilters);
            return updatedFilters;
        });
    }

    return (
        <Form>
            <Form.Label>Fields</Form.Label>
            {filters.map((filter) => (
                <Form.Check type="checkbox" key={filter.id} label={filter.name}
                            onClick={() => handleFilterSelect(filter)}/>
            ))}
        </Form>
    );
}
export default Filters;
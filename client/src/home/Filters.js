import React, {useEffect, useState} from "react";
import {Form} from "react-bootstrap";
import {getFields} from "../utils/restcalls/announcement";

function Filters({setSelectedFilters}) {
    const [filters, setFilters] = useState([]);

    useEffect(() => {
        getFields().then(data => {
            setFilters(data);
        }).catch(error => console.log(error));
    }, []);


    function handleFilterSelect(filter) {
        setSelectedFilters(prevState => {
            const updatedFilters = [...prevState];

            if (updatedFilters.includes(filter)) {
                updatedFilters.splice(updatedFilters.indexOf(filter), 1);
            } else {
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
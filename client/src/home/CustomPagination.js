import Container from "react-bootstrap/Container";
import React, {useEffect} from "react";
import {Pagination} from "react-bootstrap";

function CustomPagination({announcementsListLength, pageSize, pageNumber, setPageNumber}) {
    function handlePrevButton() {
        setPageNumber(prevState => prevState - 1);
    }

    function handleNextButton() {
        setPageNumber(prevState => prevState + 1);
    }

    useEffect(() => {
        if(announcementsListLength === pageSize) document.getElementById("next").classList.remove("disabled");
        else document.getElementById("next").classList.add("disabled");
        if(pageNumber === 0) document.getElementById("prev").classList.add("disabled");
        else document.getElementById("prev").classList.remove("disabled");

    }, [announcementsListLength, pageSize, pageNumber]);


    return (
        <Pagination className="justify-content-center">
            <Pagination.Prev id={"prev"} onClick={handlePrevButton}>Previous</Pagination.Prev>
            <Pagination.Next id={"next"} onClick={handleNextButton}>Next</Pagination.Next>
        </Pagination>
    );
}
export default CustomPagination;
import {Modal} from "react-bootstrap";
import {Calendar, DateObject} from "react-multi-date-picker";
import React, {useEffect, useState} from "react";
import {createReservation} from "../../utils/restcalls/announcement";

function AnnouncementModal({announcement, reservations, showModal, handleModalToggle}) {
    const [selectedDays, setSelectedDays] = useState([]);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleScheduleClick = async () => {
        if (selectedDays.length === 0) {
            setError("Please fill in all the details");
            return;
        }

        const selectedDates = selectedDays.map((date) => {
                let newDate = new Date(date.toString());
                let newUTCDate = new Date(newDate.getTime() - newDate.getTimezoneOffset() * 60000);
                //console.log(newUTCDate);
                return newUTCDate;
            }
        );

        // Handle the logic for scheduling here
        //console.log(selectedDays);
        const reservation = {
            announcementId: announcement.id,
            selectedDates: selectedDates,
            specialistUsername: announcement.specialistUsername,
        }

        //console.log(reservation);

        createReservation(reservation).then(data => {
            setSuccess("Reservations added successfully");
        }).catch(error => {
           setError("An error occurred. Please try again.");
        });

        //close the modal
        handleModalToggle();
    };

    const handleOwnerClick = () => {
        // Handle the logic for opening the owner's profile here
    };

    function isAvailable(date) {
        let actualDate = new Date(date.toString());
        actualDate.setHours(0, 0, 0, 0);

        // check that reservations has a reservation with the same date and not a user
        for (let i = 0; i < reservations.length; i++) {
            let reservationDate = new Date(reservations[i].date);
            reservationDate.setHours(0, 0, 0, 0);

            if (actualDate.getTime() === reservationDate.getTime() && reservations[i].isReserved === false) {
                return true;
            }
        }

        return false;
    }

    return (
        <Modal show={showModal} onHide={handleModalToggle}>
            <Modal.Header closeButton>
                <Modal.Title>{announcement.title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div>
                    <p>Owner: <span className="owner-link" onClick={handleOwnerClick}>{announcement.specialistName}</span></p>
                </div>
                <p>Specialist location: {announcement.specialistLocation}</p>
                <p>Phone number : {announcement.specialistPhoneNumber}</p>
                <p>Description: {announcement.description}</p>
                <p>Current rating: {announcement.rate}/5 &#x2605;</p>
                <p>
                    Date: {announcement.startDate} - {announcement.endDate}
                </p>
                <Calendar
                    multiple
                    value={selectedDays}
                    onChange={(value) => {
                        setSelectedDays(value)
                    }}
                    mapDays={({date}) => {

                        if (!isAvailable(date)) return {
                            disabled: true,
                            style: {color: "#ccc"},
                        }
                    }}

                    minDate={new DateObject()}
                />
                {error && <p className="text-danger">{error}</p>}
                {success && <p className="text-success">{success}</p>}
            </Modal.Body>
            <Modal.Footer>
                <button className="btn btn-primary" onClick={handleScheduleClick}>
                    Schedule
                </button>
                <button className="btn btn-secondary" onClick={handleModalToggle}>
                    Close
                </button>
            </Modal.Footer>
        </Modal>
    );
}

export default AnnouncementModal;
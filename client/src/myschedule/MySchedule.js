import React, { useEffect, useState } from 'react';
import { Table, Button, Row, Col } from 'react-bootstrap';

const MySchedule = ({ token, role }) => {
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchReservations = () => {
        console.log("MySchedule.js: role: ", role);

        const url = role ? '/my-schedules' : '/my-reservations';
        fetch(url, {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            },
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                setReservations(data);
                setLoading(false);
            })
            .catch((error) => {
                console.error('Error fetching reservations:', error);
                setLoading(false);
            });
    };

    const handleDeleteReservation = (reservationId) => {
        const url = role ? `/my-schedules/${reservationId}` : `/my-reservations/${reservationId}`;
        fetch(url, {
            method: 'DELETE',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            },
        })
            .then((response) => {
                if (response.ok) {
                    setReservations((prevReservations) =>
                        prevReservations.filter((reservation) => reservation.id !== reservationId)
                    );
                } else {
                    console.error('Failed to delete reservation');
                }
            })
            .catch((error) => {
                console.error('Error deleting reservation:', error);
            });
    };

    useEffect(() => {
        fetchReservations();
    }, []);

    if (loading) {
        return <p>Loading reservations...</p>;
    }

    if (reservations.length === 0) {
        return null; // Do not display the component if there are no reservations
    }

    return (
        <Row className="justify-content-md-center mt-5">
            <Col md={8}>
                <div>
                    <h2>{role ? 'My Schedule' : 'My reservations'}</h2>
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>Title</th>
                            <th>Date</th>
                            <th>{role ? 'Specialist First Name' : 'User First Name'}</th>
                            <th>{role ? 'Specialist Last Name' : 'User Last Name'}</th>
                            <th>{role ? 'Specialist Phone Number' : 'User Phone Number'}</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {reservations.map((reservation) => (
                            <tr key={reservation.id}>
                                <td>{reservation.title}</td>
                                <td>{reservation.date}</td>
                                <td>{role ? reservation.user.firstName : reservation.specialist.firstName}</td>
                                <td>{role ? reservation.user.lastName : reservation.specialist.lastName}</td>
                                <td>{role ? reservation.user.phoneNumber : reservation.specialist.phoneNumber}</td>
                                <td>
                                    <Button variant="danger" onClick={() => handleDeleteReservation(reservation.id)}>
                                        Delete
                                    </Button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                </div>
            </Col>
        </Row>
    );
};

export default MySchedule;

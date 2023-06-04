import React, {useEffect, useState} from 'react';
import {Table, Button, Row, Col} from 'react-bootstrap';

const MySchedule = ({token}) => {
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [deleteError, setDeleteError] = useState('');

    const fetchReservations = () => {
        const url = '/my-schedules';
        fetch(url, {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`, // Include the token as an authorization bearer
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setReservations(data);
                setLoading(false);
            })
            .catch((error) => {
                console.error('Error fetching reservations:', error);
                setLoading(false);
            });
    };

    const handleDeleteReservation = (reservationId) => {
        const url = `/my-schedules/${reservationId}`;
        fetch(url, {
            method: 'DELETE',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`, // Include the token as an authorization bearer
            },
        })
            .then((response) => {
                if (response.ok) {
                    // Remove the deleted reservation from the state
                    setReservations((prevReservations) =>
                        prevReservations.filter((reservation) => reservation.id !== reservationId)
                    );
                    //setSuccessMessage('Reservation deleted successfully');
                } else {
                    //setErrorMessage('Failed to delete reservation');
                }
            })
            .catch((error) => {
                console.error('Error deleting reservation:', error);
                //setErrorMessage('An error occurred. Please try again.');
            });
    };

    useEffect(() => {
        fetchReservations();
    }, []);

    return (
        <Row className="justify-content-md-center mt-5">
            <Col md={8}>
                <div>
                    <h2>My Schedule</h2>
                    {loading ? (
                        <p>Loading reservations...</p>
                    ) : (
                        <Table striped bordered hover>
                            <thead>
                            <tr>
                                <th>Title</th>
                                <th>Date</th>
                                <th>User First Name</th>
                                <th>User Last Name</th>
                                <th>User Phone Number</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            {reservations.map((reservation) => (
                                <tr key={reservation.id}>
                                    <td>{reservation.title}</td>
                                    <td>{reservation.date}</td>
                                    <td>{reservation.user.firstName}</td>
                                    <td>{reservation.user.lastName}</td>
                                    <td>{reservation.user.phoneNumber}</td>
                                    <td>
                                        <Button variant="danger"
                                                onClick={() => handleDeleteReservation(reservation.id)}>
                                            Delete
                                        </Button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </Table>
                    )}
                </div>
            </Col>
        </Row>
    );
};

export default MySchedule;

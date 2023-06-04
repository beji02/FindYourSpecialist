import React, {useEffect, useState} from 'react';
import {Table, Button, Row, Col} from 'react-bootstrap';

const MySchedule = ({token}) => {
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [deleteError, setDeleteError] = useState('');

    const fetchReservations = () => {
        // Fetch the reservations data from the server
        // You can make an API request to get the reservations using the token

        // Example code to simulate fetching data (replace this with your actual API request)
        setTimeout(() => {
            const reservationsData = [
                {
                    id: 1,
                    title: 'Reservation 1',
                    date: '2023-06-01',
                    user: {
                        firstName: 'John',
                        lastName: 'Doe',
                        phoneNumber: '+123456789',
                    },
                },
                {
                    id: 2,
                    title: 'Reservation 2',
                    date: '2023-06-03',
                    user: {
                        firstName: 'Jane',
                        lastName: 'Smith',
                        phoneNumber: '+987654321',
                    },
                },
            ];

            setReservations(reservationsData);
            setLoading(false);
        }, 2000);
    };

    const handleDeleteReservation = (reservationId) => {
        // Perform the deletion logic here
        // You can make an API request to delete the reservation using the reservationId

        // Example code to simulate deletion (replace this with your actual API request)
        setTimeout(() => {
            const updatedReservations = reservations.filter((reservation) => reservation.id !== reservationId);
            setReservations(updatedReservations);
            setDeleteError('');
        }, 2000);
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

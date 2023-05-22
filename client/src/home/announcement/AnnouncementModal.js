import { Modal } from "react-bootstrap";

function AnnouncementModal({ announcement, showModal, handleModalToggle }) {
    const handleScheduleClick = () => {
        // Handle the logic for scheduling here
    };

    const handleOwnerClick = () => {
        // Handle the logic for opening the owner's profile here
    };

    return (
        <Modal show={showModal} onHide={handleModalToggle}>
            <Modal.Header closeButton>
                <Modal.Title>{announcement.title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div>
                    <p>Owner: <span className="owner-link" onClick={handleOwnerClick}>{announcement.owner}</span></p>
                </div>
                <p>Description: {announcement.description}</p>
                <p>Current rating: {announcement.rate}/5 &#x2605;</p>
                <p>
                    Date: {announcement.startDate} - {announcement.endDate}
                </p>
                {/* Include any additional details here */}
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
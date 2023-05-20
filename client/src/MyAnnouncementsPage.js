import Container from "react-bootstrap/Container";
import AnnouncementsPage from "./AnnouncementsPage";

const MyAnnouncementsPage = () => {
    return (
        <Container>
            <AnnouncementsPage includeToken={true}/>
        </Container>
    );
};

export default MyAnnouncementsPage;

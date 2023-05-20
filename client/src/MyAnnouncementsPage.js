import Container from "react-bootstrap/Container";
import CustomNavbar from "./generics/CustomNavbar";
import AnnouncementsPage from "./AnnouncementsPage";
import CustomFooter from "./generics/CustomFooter";

const MyAnnouncementsPage = () => {
    return (
        <Container>
            <AnnouncementsPage includeToken={true}/>
        </Container>
    );
};

export default MyAnnouncementsPage;

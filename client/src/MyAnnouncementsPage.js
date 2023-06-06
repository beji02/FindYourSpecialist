import Container from "react-bootstrap/Container";
import AnnouncementsPage from "./AnnouncementsPage";
import AnnouncementContext from "./home/announcement/AnnouncementContext"
const MyAnnouncementsPage = () => {
    return (
        <Container>
            <AnnouncementContext.Provider value={true}>
                <AnnouncementsPage includeToken={true} page={1}/>
            </AnnouncementContext.Provider>
        </Container>
    );
};

export default MyAnnouncementsPage;

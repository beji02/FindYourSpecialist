import React, { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import CustomNavbar from "./generics/CustomNavbar";
import 'react-phone-input-2/lib/style.css';
import UserDataTile from "./profile/UserDataTile";
import BecomeSpecialistButton from "./profile/BecomeSpecialistButton";
import { isSpecialist as checkIsSpecialist } from "./utils/roles";
import SpecialistDataTile from "./profile/SpecialistDataTile";
import AnnouncementsTile from "./home/announcement/AnnouncementsTile";
import {login, upgradeToSpecialist} from "./utils/restcalls/user";

const ProfilePage = () => {
    const [isUserSpecialist, setIsUserSpecialist] = useState(false);
    const [recentlyVisitedAnnouncements, setRecentlyVisitedAnnouncements] = useState([]);

    const upgradeToSpecialistFunc = (profileForm) => {
        upgradeToSpecialist(profileForm).then(data => {
            localStorage.setItem("roles", "ROLE_SPECIALIST");
            const userRoles = localStorage.getItem("roles");
            const specialistStatus = checkIsSpecialist(userRoles);
            setIsUserSpecialist(specialistStatus);
        }).catch((error) => {
            console.error("Error upgrading to specialist:", error);
        });
    }

    const loadRecentlyVisitedAnnouncements = () => {
        const url = `users/recently-visited-announcements`;
        fetch(url, {
            method: 'GET',
                headers: {
                Accept: 'application/json',
                    'Content-Type': 'application/json',
                    Authorization: localStorage.getItem("token"),
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setRecentlyVisitedAnnouncements(data);
            })
            .catch((error) => {
                console.error("Error fetching announcements:", error);
            });
    };

    useEffect(() => {
        const userRoles = localStorage.getItem("roles");
        const specialistStatus = checkIsSpecialist(userRoles);
        setIsUserSpecialist(specialistStatus);
        loadRecentlyVisitedAnnouncements();
    }, []);

    return (
        <Container>
            <CustomNavbar />
            <UserDataTile token={localStorage.getItem("token")} />
            {!isUserSpecialist && <AnnouncementsTile title="Recently visited" announcements={recentlyVisitedAnnouncements} />}
            {!isUserSpecialist && <BecomeSpecialistButton token={localStorage.getItem("token")} upgradeToSpecialistFunc={upgradeToSpecialistFunc} />}
            {isUserSpecialist && <SpecialistDataTile token={localStorage.getItem("token")} />}
        </Container>
    );
}

export default ProfilePage;

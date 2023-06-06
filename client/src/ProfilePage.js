import React, { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import CustomNavbar from "./generics/CustomNavbar";
import 'react-phone-input-2/lib/style.css';
import UserDataTile from "./profile/UserDataTile";
import BecomeSpecialistButton from "./profile/BecomeSpecialistButton";
import { isSpecialist as checkIsSpecialist } from "./utils/roles";
import SpecialistDataTile from "./profile/SpecialistDataTile";
import AnnouncementsTile from "./home/announcement/AnnouncementsTile";
import {updateWorkInfo, getWorkInfo, upgradeToSpecialist, updatePersonalInfo, getPersonalInfo} from "./utils/restcalls/user";
import {isLogged} from "./utils/utils";
import MySchedule from "./myschedule/MySchedule";

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

    const updatePersonalInfoFunc = (personalForm) => {
        return updatePersonalInfo(personalForm);
    }

    const updateWorkInfoFunc = (workForm) => {
        return updateWorkInfo(workForm);
    }

    const getPersonalInfoFunc = () => {
        return getPersonalInfo();
    }

    const getWorkInfoFunc = () => {
        return getWorkInfo();
    }

    const loadRecentlyVisitedAnnouncements = () => {
        getRecentlyVisitedAnnouncements().then(data => {
            setRecentlyVisitedAnnouncements(data);
        }).catch((error) => {
          console.error("Error fetching recently visited announcements:", error);
        })


    };

    useEffect(() => {
        const userRoles = localStorage.getItem("roles");
        const specialistStatus = checkIsSpecialist(userRoles);
        setIsUserSpecialist(specialistStatus);
        loadRecentlyVisitedAnnouncements();
    }, []);

    return (
        <Container>
            <CustomNavbar isSpecialist={isUserSpecialist} />
            <UserDataTile token={localStorage.getItem("token")} updatePersonalInfoFunc={updatePersonalInfoFunc} getPersonalInfoFunc={getPersonalInfoFunc}/>
            <AnnouncementsTile title="Recently visited" announcements={recentlyVisitedAnnouncements} />
            {!isUserSpecialist && <BecomeSpecialistButton token={localStorage.getItem("token")} upgradeToSpecialistFunc={upgradeToSpecialistFunc} />}
            {isUserSpecialist && <SpecialistDataTile token={localStorage.getItem("token")} updateWorkInfoFunc={updateWorkInfoFunc} getWorkInfoFunc={getWorkInfoFunc} />}
            <MySchedule token={localStorage.getItem("token")} role={false} />
            {isUserSpecialist && <MySchedule token={localStorage.getItem("token")} role={isUserSpecialist} />}
        </Container>
    );
}

export default ProfilePage;

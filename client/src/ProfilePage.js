import React, { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import CustomNavbar from "./generics/CustomNavbar";
import 'react-phone-input-2/lib/style.css';
import UserDataTile from "./profile/UserDataTile";
import BecomeSpecialistButton from "./profile/BecomeSpecialistButton";
import { isSpecialist as checkIsSpecialist } from "./utils/roles";
import SpecialistDataTile from "./profile/SpecialistDataTile";

const ProfilePage = () => {
    const [isUserSpecialist, setIsUserSpecialist] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem("token");
        const userRoles = localStorage.getItem("roles");
        const specialistStatus = checkIsSpecialist(userRoles);
        setIsUserSpecialist(specialistStatus);
    }, []);

    return (
        <Container>
            <CustomNavbar />
            <UserDataTile token={localStorage.getItem("token")} />
            {!isUserSpecialist && <BecomeSpecialistButton token={localStorage.getItem("token")} />}
            {isUserSpecialist && <SpecialistDataTile token={localStorage.getItem("token")} />}
        </Container>
    );
}

export default ProfilePage;

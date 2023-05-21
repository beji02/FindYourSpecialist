import React, {useEffect, useState} from 'react';
import { Container, Row, Col, Card, Button, Form, InputGroup } from 'react-bootstrap';
import CustomNavbar from "./generics/CustomNavbar";
import 'react-phone-input-2/lib/style.css';
import UserDataTile from "./profile/UserDataTile";
import BecomeSpecialistButton from "./profile/BecomeSpecialistButton";

const ProfilePage = () => {

    return (
        <Container>
            <CustomNavbar/>
            <UserDataTile token = {localStorage.getItem("token")}/>
            <BecomeSpecialistButton token = {localStorage.getItem("token")}/>
        </Container>
    );
}

export default ProfilePage;

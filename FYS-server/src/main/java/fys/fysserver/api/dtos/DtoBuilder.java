package fys.fysserver.api.dtos;

import fys.fysmodel.Announcement;
import fys.fysmodel.Field;
import fys.fysmodel.Reservation;
import fys.fysmodel.Specialist;
import fys.fysserver.api.dtos.announcements.AnnouncementDto;
import fys.fysserver.api.dtos.announcements.FieldDto;
import fys.fysserver.api.dtos.announcements.ReservationDto;
import fys.fysserver.api.dtos.users.LoginDto;
import fys.fysserver.api.dtos.users.SpecialistDto;
import fys.fysserver.api.dtos.users.UserDto;
import fys.fysserver.api.security.services.UserDetailsImpl;

import java.util.Collection;

public class DtoBuilder {
    public static FieldDto buildFieldDTO(Field field) {
        return new FieldDto(field);
    }

    public static AnnouncementDto buildAnnouncementDTO(Announcement announcement, Boolean isFavorite) {
        return new AnnouncementDto(announcement, isFavorite);
    }

    public static ReservationDto buildReservationDTO(Reservation reservation) {
        return new ReservationDto(reservation);
    }

    public static UserDto buildUserDTO(fys.fysmodel.User user) {
        return new UserDto(user);
    }

    public static SpecialistDto buildSpecialistDTO(Specialist specialist) {
        return new SpecialistDto(specialist);
    }

    public static LoginDto buildLoginDTO(UserDetailsImpl userDetails, String token, Collection<String> roles) {
        return new LoginDto(token, userDetails.getId(), userDetails.getUsername(), userDetails.getPassword(), roles);
    }
}

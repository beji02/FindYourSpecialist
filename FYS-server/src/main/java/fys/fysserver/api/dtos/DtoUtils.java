package fys.fysserver.api.dtos;

import fys.fysmodel.Announcement;
import fys.fysmodel.Field;
import fys.fysmodel.Reservation;
import fys.fysmodel.Specialist;
import fys.fysserver.api.dtos.announcements.*;
import fys.fysmodel.Notification;
import fys.fysserver.api.dtos.announcements.AnnouncementDto;
import fys.fysserver.api.dtos.announcements.FieldDto;
import fys.fysserver.api.dtos.announcements.ReservationDto;
import fys.fysserver.api.dtos.users.LoginDto;
import fys.fysserver.api.dtos.users.NotificationDto;
import fys.fysserver.api.dtos.users.SpecialistDto;
import fys.fysserver.api.dtos.users.UserDto;
import fys.fysserver.api.security.services.UserDetailsImpl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DtoUtils {
    private static final Logger logger = LoggerFactory.getLogger(DtoUtils.class);

    public static FieldDto buildFieldDTO(Field field) {
        logger.info("Building FieldDto");
        return new FieldDto(field);
    }

    public static AnnouncementDto buildAnnouncementDTO(Announcement announcement, Boolean isFavorite) {
        logger.info("Building AnnouncementDto");
        return new AnnouncementDto(announcement, isFavorite);
    }

    public static ReservationDto buildReservationDTO(Reservation reservation) {
        logger.info("Building ReservationDto");
        return new ReservationDto(reservation);
    }

    public static UserDto buildUserDTO(fys.fysmodel.User user) {
        logger.info("Building UserDto");
        return new UserDto(user);
    }

    public static SpecialistDto buildSpecialistDTO(Specialist specialist) {
        logger.info("Building SpecialistDto");
        return new SpecialistDto(specialist);
    }

    public static LoginDto buildLoginDTO(UserDetailsImpl userDetails, String token, Collection<String> roles) {
        logger.info("Building LoginDto");
        return new LoginDto(token, userDetails.getId(), userDetails.getUsername(), userDetails.getPassword(), roles);
    }

    public static NotificationDto buildNotificationDTO(fys.fysmodel.Notification notification) {
        logger.info("Building NotificationDto");
        return new NotificationDto(notification);
    }

    public static Notification buildNotification(NotificationDto notificationDto) {
        logger.info("Building Notification");
        return new Notification(notificationDto.getId(), notificationDto.getTitle(), notificationDto.getText(), notificationDto.getDate(), notificationDto.isRead());
    }

    public static ScheduledReservationDto buildScheduledReservationDto(Reservation reservation) {
        logger.info("Building ScheduledReservationDto");
        return new ScheduledReservationDto(reservation);
    }

    public static MyReservationDto buildMyReservationDto(Reservation reservation) {
        logger.info("Building MyReservationDto");
        return new MyReservationDto(reservation);
    }
}

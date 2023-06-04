package fys.fysserver.api.dtos.announcements;

import fys.fysmodel.Reservation;
import fys.fysmodel.User;

import java.time.LocalDate;

public class MyReservationDto {
    private Integer id;
    private LocalDate date;
    private String title;

    private CustomSpecialistDto specialist;

    public class CustomSpecialistDto {
        private String firstName;
        private String lastName;
        private String phoneNumber;

        public CustomSpecialistDto() {
        }

        public CustomSpecialistDto(User user) {
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.phoneNumber = user.getPhoneNumber();
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    };

    public MyReservationDto() {
    }

    public MyReservationDto(Reservation reservation) {
        this.id = reservation.getId();
        this.date = reservation.getDate();
        this.title = reservation.getAnnouncement().getTitle();
        this.specialist = new CustomSpecialistDto(reservation.getUser());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CustomSpecialistDto getSpecialist() {
        return specialist;
    }

    public void setSpecialist(CustomSpecialistDto specialist) {
        this.specialist = specialist;
    }
}

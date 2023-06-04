package fys.fysserver.api.dtos.announcements;

import fys.fysmodel.Reservation;

import java.time.LocalDate;

public class ReservationDto {
    private LocalDate date;
    private Boolean isReserved;

    public ReservationDto(Reservation reservation) {
        this.date = reservation.getDate();
        this.isReserved = reservation.getUser() != null;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(Boolean reserved) {
        isReserved = reserved;
    }
}

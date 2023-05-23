package fys.fysserver.api.dto;

import java.time.LocalDate;
import java.util.Set;

public class ReservationDTO {
    Integer announcementId;
    Set<LocalDate> selectedDates;

    public ReservationDTO(Integer announcementId, Set<LocalDate> selectedDates) {
        this.announcementId = announcementId;
        this.selectedDates = selectedDates;
    }

    public Integer getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }

    public Set<LocalDate> getSelectedDates() {
        return selectedDates;
    }

    public void setSelectedDates(Set<LocalDate> selectedDates) {
        this.selectedDates = selectedDates;
    }
}

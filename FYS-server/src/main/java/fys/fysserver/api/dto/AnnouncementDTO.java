package fys.fysserver.api.dto;

import fys.fysmodel.Announcement;
import fys.fysmodel.Field;
import fys.fysmodel.Specialist;

import java.time.LocalDate;

public class AnnouncementDTO extends Announcement {
    private boolean isFavorite;

    public AnnouncementDTO(Integer id, Float rate, String description, String title, LocalDate startDate, LocalDate endDate, Field field, Specialist specialist, boolean isFavorite) {
        super(id, rate, description, title, startDate, endDate, specialist, field);
        this.isFavorite = isFavorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "AnnouncementDTO{" +
                "isFavourite=" + isFavorite +
                '}';
    }
}

package fys.fysserver.api.dtos.announcements;

import java.time.LocalDate;
import java.util.Set;

public class NewAnnouncementDto {
    private String title;
    private String description;
    private Integer fieldId;
    private Set<LocalDate> workDays;

    public NewAnnouncementDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Set<LocalDate> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Set<LocalDate> workDays) {
        this.workDays = workDays;
    }


    @Override
    public String toString() {
        return "NewAnnouncementDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", fieldId=" + fieldId +
                ", workDays=" + workDays +
                '}';
    }
}





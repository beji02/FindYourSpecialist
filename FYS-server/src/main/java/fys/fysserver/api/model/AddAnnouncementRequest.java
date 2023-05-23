package fys.fysserver.api.model;

import java.time.LocalDate;
import java.util.Set;

public class AddAnnouncementRequest {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    private Set<LocalDate> workDays;
    private Integer fieldId;


    public AddAnnouncementRequest() {
    }

    public AddAnnouncementRequest(String title, String description, LocalDate startDate, LocalDate endDate, Integer fieldId) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fieldId = fieldId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer field_id) {
        this.fieldId = field_id;
    }


    public Set<LocalDate> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Set<LocalDate> workDays) {
        this.workDays = workDays;
    }
}

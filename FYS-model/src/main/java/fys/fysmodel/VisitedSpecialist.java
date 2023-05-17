package fys.fysmodel;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="visited_specialists")
@Inheritance(strategy = InheritanceType.JOINED)
public class VisitedSpecialist extends Specialist {
    private LocalDateTime dateTime;

    public VisitedSpecialist() {}

    public VisitedSpecialist(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public VisitedSpecialist(Integer area, String location, String description, LocalDateTime dateTime) {
        super(area, location, description);
        this.dateTime = dateTime;
    }

    public VisitedSpecialist(String username, String password, String firstName, String lastName, LocalDate birthDate, String phoneNumber, String optionalDescription, Integer area, String location, String description, LocalDateTime dateTime) {
        super(username, password, firstName, lastName, birthDate, phoneNumber, optionalDescription, area, location, description);
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}

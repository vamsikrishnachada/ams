package ams.business.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class TimeSlot {
    @Id
    @GeneratedValue
    private UUID timeSlotId;

    private String time;
    private boolean isAppointment;
    private boolean isService;

    public TimeSlot() {
    }

    public TimeSlot(String time) {
        this.time = time;
        this.isAppointment = false;
        this.isService = false;
    }
}


package ams.business.model;

import ams.business.model.constants.TimeToIndex;
import ams.client.model.Persistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class DayDateTime extends Persistence {
    @Id
    @GeneratedValue
    private UUID dayDateTimeId;
    private LocalDate date;
    private DayOfWeek dayOfWeek;

    @ManyToOne
    private Mechanic mechanic;

//    private Map<String, Map<String, Boolean>> timeMap;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "day_date_time_id")
    @MapKeyColumn(name = "time_key")
    private Map<String, TimeSlot> timeMap;

    static {
        TimeToIndex.initialize();
    }

    public DayDateTime(LocalDate date) {
        this.date = date;
        this.dayOfWeek = date.getDayOfWeek();
        this.timeMap = new HashMap<>();
        for (int i = 9; i < 17; i++) {
            String hour = String.format("%02d", i);
            TimeSlot timeSlot = new TimeSlot(hour + ":00");
            timeMap.put(hour + ":00", timeSlot);

            timeSlot = new TimeSlot(hour + ":30");
            timeMap.put(hour + ":30", timeSlot);
        }
        TimeSlot timeSlot = new TimeSlot("17:00");
        timeMap.put("17:00", timeSlot);
    }

    public void setAppointmentHours(String startTime, String endTime) {
        try {
            TimeToIndex.initialize(); // Initialize TimeToIndex class

            int startIndex = TimeToIndex.getTimeToIndex().get(startTime);
            int endIndex = TimeToIndex.getTimeToIndex().get(endTime);

            if (startIndex == -1 || endIndex == -1) {
                throw new InvalidTimeException("Invalid start or end time.");
            }

            for (int i = startIndex; i <= endIndex; i++) {
                String time = TimeToIndex.getIndexToTime().get(i);
                if (time != null) {
                    TimeSlot timeSlot = timeMap.get(time);
                    if (timeSlot != null) {
                        timeSlot.setAppointment(true);
                    }
                }
            }
        } catch (InvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setServiceHours(String startTime, String endTime) {
        try {
            TimeToIndex.initialize(); // Initialize TimeToIndex class

            int startIndex = TimeToIndex.getTimeToIndex().get(startTime);
            int endIndex = TimeToIndex.getTimeToIndex().get(endTime);

            if (startIndex == -1 || endIndex == -1) {
                throw new InvalidTimeException("Invalid start or end time.");
            }

            for (int i = startIndex; i <= endIndex; i++) {
                String time = TimeToIndex.getIndexToTime().get(i);
                if (time != null) {
                    TimeSlot timeSlot = timeMap.get(time);
                    if (timeSlot != null) {
                        timeSlot.setService(true);
                    }
                }
            }
        } catch (InvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public class InvalidTimeException extends RuntimeException {
        public InvalidTimeException(String message) {
            super(message);
        }
    }
}
